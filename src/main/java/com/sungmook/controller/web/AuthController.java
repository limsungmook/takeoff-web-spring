package com.sungmook.controller.web;

import com.sungmook.aop.annotation.GetOutLoginUser;
import com.sungmook.domain.SessionUser;
import com.sungmook.domain.SignupMember;
import com.sungmook.helper.MessageHelper;
import com.sungmook.service.MemberService;
import javassist.tools.web.BadHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Controller
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MessageHelper messageHelper;


    @Autowired
    private MemberService memberService;

    @GetOutLoginUser
    @RequestMapping("/auth/login")
    public String login(SessionUser sessionUser) throws BadHttpRequest {
        return "/auth/login";
    }

    @PreAuthorize("isAnonymous()")
    @RequestMapping(path="/auth/signup", method = RequestMethod.GET)
    public String signup(SignupMember signupMember) throws BadHttpRequest {


        return "/auth/signup";
    }

    @RequestMapping(path="/auth/signup", method = RequestMethod.POST)
    public String signup(@Valid SignupMember signupMember, BindingResult bindingResult) throws BadHttpRequest {

        logger.debug("가입 요청");

        if( bindingResult.hasErrors() ){
            return "/auth/signup";
        }

        if( signupMember.getPassword().equals(signupMember.getReTypePassword()) == false ){
            bindingResult.rejectValue("reTypePassword", "{com.sungmook.domain.SignupMember.reTypePassword.NotMatch}", "입력하신 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
            return "/auth/signup";
        }

        memberService.signup(signupMember.buildMember());

        return "redirect:/auth/signup_success";
    }

    @RequestMapping(path="/auth/signup_success", method = RequestMethod.GET)
    public String signupSuccess(){
        return "/auth/signup_success";
    }

}
