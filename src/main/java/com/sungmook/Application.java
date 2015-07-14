package com.sungmook;


import com.sungmook.domain.Role;
import com.sungmook.domain.SignupMember;
import com.sungmook.repository.MemberRepository;
import com.sungmook.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@SpringBootApplication
public class Application {



    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "true");
        SpringApplication.run(Application.class, args);
    }

    // After Start

    private final Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void applicationInit(){

        /**
         *  기본 Roles 세팅
         */
        List<Role> roles = roleRepository.findAll();
        if( roles == null || roles.isEmpty() ){
            for( Role.Value value : Role.Value.values() ){
                roleRepository.saveAndFlush(Role.buildFromValue(value));
            }
        }

        /**
         * 관리자 계정 Init
         * admin/admin
         */
        String adminUsername = "admin@takeoff.com";
        if( memberRepository.findByUsername(adminUsername) == null ){
            SignupMember admin = new SignupMember();
            admin.setUsername(adminUsername);
            admin.setPassword("admin");

            admin
                    .addRole(Role.buildFromValue(Role.Value.ADMIN))
                    .addRole(Role.buildFromValue(Role.Value.USER))
                    .removeRole(Role.buildFromValue(Role.Value.INACTIVE_USER));

            memberRepository.save(admin.buildMember());
        }
    }

}
