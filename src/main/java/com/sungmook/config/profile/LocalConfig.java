package com.sungmook.config.profile;

import org.h2.server.web.WebServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;


/**
 * http://stackoverflow.com/questions/24655684/spring-boot-default-h2-jdbc-connection-and-h2-console
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Configuration
@Profile("local")
@SuppressWarnings(value = "unused")
public class LocalConfig {

    /**
     * h2 Memory DB 의 콘솔을 열어준다.
     * {서버URL}/console/ 로 접속할 수 있다.
     */
    @Bean
    public ServletRegistrationBean h2servletRegistration() throws SQLException {
        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
        registration.addUrlMappings("/console/*");
        return registration;
    }

}
