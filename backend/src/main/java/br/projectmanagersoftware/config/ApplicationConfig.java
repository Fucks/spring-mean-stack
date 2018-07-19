package br.projectmanagersoftware.config;

import br.projectmanagersoftware.config.application.MailConfiguration;
import br.projectmanagersoftware.config.data.PersistenceConfig;
import br.projectmanagersoftware.config.security.MethodSecurityConfig;
import br.projectmanagersoftware.config.security.OAuth2ServerConfiguration;
import br.projectmanagersoftware.config.security.SecurityConfig;
import br.projectmanagersoftware.config.web.WebMvcConfig;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 29/10/2015
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan("br.projectmanagersoftware")
@Import({
    PersistenceConfig.class,
    SecurityConfig.class,
    OAuth2ServerConfiguration.class,
    MethodSecurityConfig.class,
    WebMvcConfig.class,
    MailConfiguration.class
})
public class ApplicationConfig {

    private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";

    @Bean(name = "fieldsMessageSource")
    MessageSource fieldsMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/fields");
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }

    @Bean(name = "exceptionMessageSource")
    MessageSource exceptionMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/exception");
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }

    @Bean
    PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
