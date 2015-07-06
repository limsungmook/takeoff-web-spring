package com.sungmook.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.annotation.PostConstruct;
import java.util.Properties;

/**
 * spring boot 의 기본 설정을 따른다.
 * MailSender 등은 기본으로 세팅이 된다.
 * 여기선 템플릿 엔진인 Velocity 만 초기화한다.
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Configuration
public class MailConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment environment;

    @Value("${mail.from}")
    private String from;
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private Integer port;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
//    @Value("${spring.mail.properties}")
//    private Map<String, String> properties;


    @Bean
    public JavaMailSender javaMailSender() {

        username = environment.getProperty("TAKEOFF_MAIL_USERNAME", username);
        password = environment.getProperty("TAKEOFF_MAIL_PASSWORD", password);

        logger.debug("email username : {}", username);
        logger.debug("email password : {}", password);

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties mailProperties = new Properties();

        mailSender.setProtocol("smtp");
        mailSender.setHost(host);
        int port = environment.getProperty("spring.mail.port", Integer.class, 25);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        boolean auth = environment.getProperty("spring.mail.properties.mail.smtp.auth", Boolean.class, false);

        if( auth == true ){

            mailProperties.put("mail.smtp.auth", auth);

            boolean starttlsEnable = environment.getProperty("spring.mail.properties.mail.smtp.starttls.enable", Boolean.class, true);
            mailProperties.put("mail.smtp.starttls.enable", starttlsEnable);

            int tlsPort = environment.getProperty("spring.mail.properties.mail.smtp.socketFactory.port", Integer.class, 445);
            mailProperties.put("mail.smtp.socketFactory.port", tlsPort);
//
            String tlsClass = environment.getProperty("spring.mail.properties.mail.smtp.socketFactory.class", String.class, "javax.net.ssl.SSLSocketFactory");
            mailProperties.put("mail.smtp.socketFactory.class", tlsClass);
//
            boolean tlsFallback = environment.getProperty("spring.mail.properties.mail.smtp.socketFactory.fallback", Boolean.class, false);
            mailProperties.put("mail.smtp.socketFactory.fallback", tlsFallback);

        }
        mailProperties.putAll(mailProperties);
        mailSender.setJavaMailProperties(mailProperties);
        return mailSender;
    }

    @PostConstruct
    public void init(){
    }
}
