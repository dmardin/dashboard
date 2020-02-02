package edu.hm.hafner.warningsngui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration to specify the Messages of the Application.
 *
 * @author Deniz Mardin
 */
@Configuration
public class WarningsNgUiConfig implements WebMvcConfigurer {

    /**
     * Specify the location of the Messages file for this application (messages/content) in the resource folder.
     *
     * @return the configured {@link ResourceBundleMessageSource}
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages/content");
        return messageSource;
    }
}
