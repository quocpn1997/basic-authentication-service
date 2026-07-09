package rey.be.authenticationservice.infra.mail;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import rey.be.authenticationservice.infra.config.MailProperties;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerificationEmailSenderTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private MailProperties mailProperties;

    @Mock
    private MailTemplateRenderer mailTemplateRenderer;

    @InjectMocks
    private VerificationEmailSender verificationEmailSender;

    @Test
    void send_buildsMessageFromTemplates_andSends() {
        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((jakarta.mail.Session) null));
        when(mailProperties.getFrom()).thenReturn("no-reply@example.com");
        when(mailTemplateRenderer.render(anyString(), anyMap())).thenReturn("rendered body");

        verificationEmailSender.send("john.doe@example.com", "123456");

        verify(javaMailSender).send(any(MimeMessage.class));
    }

    @Test
    void send_logsAndSwallows_whenMailSendingFails() {
        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((jakarta.mail.Session) null));
        when(mailProperties.getFrom()).thenReturn("no-reply@example.com");
        when(mailTemplateRenderer.render(anyString(), anyMap())).thenReturn("rendered body");
        doThrow(new MailSendException("smtp down")).when(javaMailSender).send(any(MimeMessage.class));

        assertThatCode(() -> verificationEmailSender.send("john.doe@example.com", "123456"))
                .doesNotThrowAnyException();
    }
}
