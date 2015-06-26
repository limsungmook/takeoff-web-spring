package com.sungmook;


import com.sungmook.domain.Member;
import com.sungmook.domain.Role;
import com.sungmook.repository.MemberRepository;
import com.sungmook.repository.RoleRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
        SpringApplication.run(Application.class, args);
    }

    // After Start

    private final Log logger = LogFactory.getLog(getClass());

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
            roleRepository.saveAndFlush(Role.asAdmin());
            roleRepository.saveAndFlush(Role.asUser());
        }

        /**
         * 관리자 계정 Init
         * admin/admin
         */
        Member admin = memberRepository.findByUsername("admin");
        if( admin == null ){
            admin = new Member();
            admin.setUsername("admin");
            admin.setPassword("admin");

            admin.addRole(Role.asAdmin()).addRole(Role.asUser());
            memberRepository.save(admin);
        }
    }

}
