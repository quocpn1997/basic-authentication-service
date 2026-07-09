package rey.be.authenticationservice.infra.mail;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MailTemplateRendererTest {

    private static final String TEMPLATE = "templates/test-render-template.txt";

    private final MailTemplateRenderer renderer = new MailTemplateRenderer();

    @Test
    void render_substitutesAllPlaceholders() {
        var result = renderer.render(TEMPLATE, Map.of("name", "Jane", "code", "123456"));

        assertThat(result).contains("Hello Jane, your code is 123456.");
    }

    @Test
    void render_leavesUnknownPlaceholdersUntouched() {
        var result = renderer.render(TEMPLATE, Map.of("code", "654321"));

        assertThat(result).contains("{{name}}").contains("654321");
    }

    @Test
    void render_usesCachedTemplate_onSecondCall() {
        var first = renderer.render(TEMPLATE, Map.of("name", "A", "code", "111111"));
        var second = renderer.render(TEMPLATE, Map.of("name", "B", "code", "222222"));

        assertThat(first).contains("A", "111111");
        assertThat(second).contains("B", "222222");
    }

    @Test
    void render_throws_whenTemplateDoesNotExist() {
        assertThatThrownBy(() -> renderer.render("templates/does-not-exist.txt", Map.of()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("does-not-exist.txt");
    }
}
