package com.sungmook.config.profile;

import com.sungmook.domain.Role;
import com.sungmook.domain.SignupMember;
import com.sungmook.domain.Story;
import com.sungmook.repository.MemberRepository;
import com.sungmook.repository.RoleRepository;
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
import java.util.List;


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
    private MemberRepository memberRepository;

    @Autowired
    private StoryService storyService;

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


        /**
         *  만약 Roles 가 없으면 기본 Roles 세팅
         */
        List<Role> roles = roleRepository.findAll();
        if( roles == null || roles.isEmpty() ){
            for( Role.Value value : Role.Value.values() ){
                roleRepository.saveAndFlush(Role.buildFromValue(value));
            }
        }


        SignupMember user = new SignupMember();
        user.setUsername("asdf@asdf.com");
        user.setPassword("asdf");

        user
                .addRole(Role.buildFromValue(Role.Value.USER))
                .removeRole(Role.buildFromValue(Role.Value.INACTIVE_USER));

        memberRepository.save(user.buildMember());

        SignupMember sungmook = new SignupMember();
        sungmook.setUsername("ipes4579@gmail.com");
        sungmook.setPassword("asdf");

        sungmook
                .addRole(Role.buildFromValue(Role.Value.USER))
                .removeRole(Role.buildFromValue(Role.Value.INACTIVE_USER));

        memberRepository.save(sungmook.buildMember());

        Story story = new Story();
        story.setTitle("테스트 타이틀");
        story.setRawText("테스트 텍스트");
        storyService.save(story);
    }
}
