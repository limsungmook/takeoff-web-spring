package com.sungmook.controller.web;

import com.sungmook.aop.annotation.GetOutLoginUser;
import com.sungmook.domain.AuthToken;
import com.sungmook.security.SessionUser;
import com.sungmook.domain.SignupUser;
import com.sungmook.helper.MessageHelper;
import com.sungmook.service.AuthTokenService;
import com.sungmook.service.UserService;
import javassist.tools.web.BadHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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
    private UserService userService;

    @Autowired
    private AuthTokenService authTokenService;

    @GetOutLoginUser
    @RequestMapping("/auth/login")
    public String login(SessionUser sessionUser) throws BadHttpRequest {
        return "/auth/login";
    }

    @PreAuthorize("isAnonymous()")
    @RequestMapping(path="/auth/signup", method = RequestMethod.GET)
    public String signup(SignupUser signupMember) throws BadHttpRequest {


        return "/auth/signup";
    }

    @RequestMapping(path="/auth/signup", method = RequestMethod.POST)
    public String signup(@Valid SignupUser signupMember, BindingResult bindingResult) throws BadHttpRequest {

        logger.debug("가입 요청");

        if( bindingResult.hasErrors() ){
            return "/auth/signup";
        }

        if( signupMember.getPassword().equals(signupMember.getReTypePassword()) == false ){
            bindingResult.rejectValue("reTypePassword", "{com.sungmook.domain.SignupMember.reTypePassword.NotMatch}", "입력하신 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
            return "/auth/signup";
        }

        userService.signup(signupMember.buildUser());

        return "redirect:/auth/signup_success";
    }

    @RequestMapping(path="/auth/signup_success", method = RequestMethod.GET)
    public String signupSuccess(){
        return "/auth/signup_success";
    }

    @GetOutLoginUser
    @RequestMapping(path="/auth/find_password", method = RequestMethod.GET)
    public String findPassword(){
        return "/auth/find_password";
    }

    @GetOutLoginUser
    @RequestMapping(path="/auth/find_password", method = RequestMethod.POST)
    public String findPassword(String email,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes){
        authTokenService.generateAndSendMail(AuthToken.Type.FIND_PASSWORD, email);
        redirectAttributes.addAttribute("email", email);

        return "redirect:/auth/sent_reset_password";
    }
    @GetOutLoginUser
    @RequestMapping(path="/auth/sent_reset_password", method = RequestMethod.GET)
    public String sentResetPassword(HttpServletRequest request){
        return "/auth/sent_reset_password";
    }

}
