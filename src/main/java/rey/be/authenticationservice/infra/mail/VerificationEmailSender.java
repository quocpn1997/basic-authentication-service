package rey.be.authenticationservice.infra.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import rey.be.authenticationservice.infra.config.MailProperties;

import java.util.Map;

/**
 * Sends verification emails on a background thread so callers are not blocked by SMTP latency.
 * Failures are logged rather than propagated, since the HTTP response has already been returned.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class VerificationEmailSender {

    private static final String HTML_TEMPLATE = "templates/email/verification.html";

    private static final String TEXT_TEMPLATE = "templates/email/verification.txt";

    private static final String SUBJECT = "Your verification code";

    private final JavaMailSender javaMailSender;

    private final MailProperties mailProperties;

    private final MailTemplateRenderer mailTemplateRenderer;

    @Async("emailTaskExecutor")
    public void send(String email, String verificationCode) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
            helper.setFrom(mailProperties.getFrom());
            helper.setTo(email);
            helper.setSubject(SUBJECT);
            helper.setText(render(TEXT_TEMPLATE, verificationCode), render(HTML_TEMPLATE, verificationCode));
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | MailException e) {
            log.error("Failed to send verification email to {}", email, e);
        }
    }

    private String render(String templatePath, String verificationCode) {
        return mailTemplateRenderer.render(templatePath, Map.of("code", verificationCode));
    }
}
