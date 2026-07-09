package rey.be.authenticationservice.infra.mail;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MailTemplateRenderer {

    private final Map<String, String> cache = new ConcurrentHashMap<>();

    public String render(String templatePath, Map<String, String> variables) {
        var content = cache.computeIfAbsent(templatePath, this::load);
        for (var variable : variables.entrySet()) {
            content = content.replace("{{" + variable.getKey() + "}}", variable.getValue());
        }
        return content;
    }

    private String load(String templatePath) {
        try (var inputStream = new ClassPathResource(templatePath).getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load email template: " + templatePath, e);
        }
    }
}
