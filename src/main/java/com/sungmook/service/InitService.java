package com.sungmook.service;

import com.sungmook.domain.*;
import com.sungmook.repository.*;
import com.sungmook.security.LoginHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Service
public class InitService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ScopeRepository scopeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScopeService scopeService;

    @Autowired
    private LoginHelper loginHelper;

    @Autowired
    private StoryService storyService;

    @Autowired
    private ActRepository actRepository;

    @Autowired
    private StickerRepository stickerRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void checkAndInitApplication(){
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
         * 기본 스티커
         */
        Sticker sticker = null;
        if( stickerRepository.findAll().isEmpty() ){
            sticker = new Sticker();
            sticker.setType(Sticker.Type.DEFAULT);
            sticker.setNegativeImageUrl("/img/sticker/default_negative.png");
            sticker.setNormalImageUrl("/img/sticker/default_normal.png");
            sticker.setPositiveImageUrl("/img/sticker/default_positive.png");
            stickerRepository.saveAndFlush(sticker);
        }

        /**
         * 기본 Act
         */
        List<Act> actList = actRepository.findAll();
        if( actList == null || actList.isEmpty() ){
            Act negativeAct = new Act();
            negativeAct.setType(Act.Type.NEGATIVE);
            negativeAct.setSticker(sticker);
            negativeAct.setImageUrl(sticker.getNegativeImageUrl());
            actRepository.saveAndFlush(negativeAct);

            Act normalAct = new Act();
            normalAct.setType(Act.Type.NORMAL);
            normalAct.setSticker(sticker);
            normalAct.setImageUrl(sticker.getNormalImageUrl());
            actRepository.saveAndFlush(normalAct);

            Act positiveAct = new Act();
            positiveAct.setType(Act.Type.NEGATIVE);
            positiveAct.setSticker(sticker);
            positiveAct.setImageUrl(sticker.getPositiveImageUrl());
            actRepository.saveAndFlush(positiveAct);
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

            User user = admin.buildUser();
            userRepository.save(user);

            userService.confirmSignup(user.getId());
//            scopeService.setupDefaults(user);

        }
    }

    @Transactional
    public void initTest() {

        SignupUser user = new SignupUser();
        user.setUsername("asdf@asdf.com");
        user.setPassword("asdf");

        user
                .addRole(Role.buildFromValue(Role.Value.USER))
                .removeRole(Role.buildFromValue(Role.Value.INACTIVE_USER));

        userRepository.save(user.buildUser());

        loginHelper.login(user.getUsername());

        for(int i = 0; i < 5; i++){
            // 테스트 스토리들 등록
            Story story = new Story();
            story.setTitle("asdf 의 글");
            story.setRawText("asdf의  텍스트");
            storyService.save(story);
        }

        SignupUser sungmook = new SignupUser();
        sungmook.setUsername("ipes4579@gmail.com");
        sungmook.setPassword("asdf");

        sungmook
                .addRole(Role.buildFromValue(Role.Value.USER))
                .removeRole(Role.buildFromValue(Role.Value.INACTIVE_USER));

        userRepository.save(sungmook.buildUser());


        loginHelper.login(sungmook.getUsername());

        for(int i = 0; i < 6; i++){
            // 테스트 스토리들 등록
            Story story = new Story();
            story.setTitle("테스트 타이틀");
            story.setRawText("테스트 텍스트");
            storyService.save(story);
        }

    }
}
