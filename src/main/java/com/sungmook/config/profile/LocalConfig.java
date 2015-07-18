package com.sungmook.config.profile;

import com.sungmook.repository.RoleRepository;
import com.sungmook.repository.ScopeRepository;
import com.sungmook.repository.UserRepository;
import com.sungmook.security.LoginHelper;
import com.sungmook.service.InitService;
import com.sungmook.service.ScopeService;
import com.sungmook.service.StoryService;
import org.h2.server.web.WebServlet;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import java.sql.SQLException;


/**
 * http://stackoverflow.com/questions/24655684/spring-boot-default-h2-jdbc-connection-and-h2-console
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Configuration
@Profile("local")
@SuppressWarnings(value = "unused")
public class LocalConfig {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoryService storyService;

    @Autowired
    private LoginHelper loginHelper;

    @Autowired
    private ScopeService scopeService;

    @Autowired
    private ScopeRepository scopeRepository;


    @Autowired
    private InitService initService;


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


    @PostConstruct
    public void init(){

        initService.checkAndInitApplication();
        initService.initTest();
    }
}
