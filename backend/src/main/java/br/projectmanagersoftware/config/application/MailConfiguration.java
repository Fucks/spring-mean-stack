package br.projectmanagersoftware.config.application;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 05/10/2016
 */
@Configuration
@PropertySource("classpath:application.properties")
@EnableAsync
public class MailConfiguration {
    
    @Autowired
    private Environment env;

    @Bean(name = "mailSender")
    public JavaMailSender mailSender(){
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        mailSender.setHost(env.getProperty("mail.host"));
        mailSender.setPort(Integer.parseInt(env.getProperty("mail.port")));
        mailSender.setUsername(env.getProperty("mail.username"));
        mailSender.setPassword(env.getProperty("mail.password"));
        mailSender.setDefaultEncoding("UTF-8");
        
        final Properties properties = new Properties();
        properties.put("mail.smtp.auth", env.getProperty("mail.smtp.auth"));
        properties.put("mail.smtp.debug", env.getProperty("mail.smtp.debug"));
        properties.put("mail.smtp.starttls.enable", env.getProperty("mail.smtp.starttls.enable"));
        properties.put("mail.smtps.ssl.enable", env.getProperty("mail.smtp.starttls.enable"));
        
        mailSender.setJavaMailProperties(properties);
        
        return mailSender;
    }
    
     /*
     * FreeMarker configuration.
     */
    @Bean
    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("classpath:/templates/");
        return bean;
    }
}
