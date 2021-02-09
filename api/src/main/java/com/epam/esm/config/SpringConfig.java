package com.epam.esm.config;

import com.epam.esm.jpa.criteria.GiftCriteriaBuilder;
import com.epam.esm.jpa.criteria.TagCriteriaBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Configuration
@PropertySource({
        "classpath:${spring.profiles.active}.db.properties",
        "classpath:application.properties"})
@EnableTransactionManagement
public class SpringConfig implements WebMvcConfigurer {
    private final ApplicationContext applicationContext;

    @Value("${message-source.basename}")
    private String messageSourceBaseName;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }

    @PostConstruct
    public ObjectMapper jacksonObjectMapper() {
        ObjectMapper objectMapper = objectMapper();
        objectMapper.registerModule(new JsonNullableModule());
        return objectMapper;
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(messageSourceBaseName);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setDefaultLocale(Locale.ENGLISH);
        messageSource.setUseCodeAsDefaultMessage(false);
        return messageSource;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MessageSourceResourceBundleLocator resourceBundle() {
        return new MessageSourceResourceBundleLocator(messageSource());
    }

    @Bean
    public ResourceBundleMessageInterpolator interpolator() {
        return new ResourceBundleMessageInterpolator(resourceBundle());
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setMessageInterpolator(interpolator());
        return bean;
    }

    @Bean
    public GiftCriteriaBuilder giftCriteriaBuilder() {
        return new GiftCriteriaBuilder();
    }

    @Bean
    public TagCriteriaBuilder tagCriteriaBuilder() {
        return new TagCriteriaBuilder();
    }
}
