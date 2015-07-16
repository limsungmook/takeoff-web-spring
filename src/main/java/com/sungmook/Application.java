package com.sungmook;


import com.sungmook.domain.Role;
import com.sungmook.domain.Scope;
import com.sungmook.domain.SignupUser;
import com.sungmook.domain.User;
import com.sungmook.repository.RoleRepository;
import com.sungmook.repository.ScopeRepository;
import com.sungmook.repository.UserRepository;
import com.sungmook.service.ScopeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
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

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ScopeService scopeService;

    @Autowired
    private ScopeRepository scopeRepository;

    @PostConstruct
    @Transactional
    public void applicationInit(){

        /**
         * 기본 공개 세팅
         */
        Scope scope = scopeRepository.findByType(Scope.Type.GLOBAL);
        if( scope == null ){
            scope = new Scope(Scope.Type.GLOBAL);
            scope.setName("전체공개");
            scopeRepository.save(scope);
        }
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
        if( userRepository.findByUsername(adminUsername) == null ){
            SignupUser admin = new SignupUser();
            admin.setUsername(adminUsername);
            admin.setPassword("admin");

            admin
                    .addRole(Role.buildFromValue(Role.Value.ADMIN))
                    .addRole(Role.buildFromValue(Role.Value.USER))
                    .removeRole(Role.buildFromValue(Role.Value.INACTIVE_USER));

            logger.debug("빌드 멤버 : {}", admin);

            User user = admin.buildUser();
            userRepository.save(user);

            scopeService.setupDefaults(user);

        }



    }

}
