package com.sungmook.controller.web;

import com.sungmook.domain.AuthToken;
import com.sungmook.exception.CommonException;
import com.sungmook.exception.InvalidTokenException;
import com.sungmook.service.AuthTokenService;
import com.sungmook.service.UserService;
import javassist.tools.web.BadHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Controller
public class ConfirmController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    private UserService userService;

    @RequestMapping("/confirm")
    public String confirm(
            @RequestParam(value = "mid", required = true) Long memberId,
            @RequestParam(value = "token", required = true) String token,
            Model model) throws BadHttpRequest {

        AuthToken authToken;

        try {
            authToken = authTokenService.validAndGet(memberId, token);
        }catch( InvalidTokenException ex ){
            return "/confirm/invalid";
        }

        switch ( authToken.getType() ){

            case SIGNUP:

                return "redirect:/confirm/success_signup";

            case FIND_PASSWORD:
                model.addAttribute(authToken);
                return "/confirm/reset_password";
        }
        return "redirect:/confirm/success_signup";
    }

    @RequestMapping(value = "/confirm/reset_password", method = RequestMethod.POST)
    public String resetPassword(
            String password,
            String reTypePassword,
            Long authTokenId,
            Long memberId,
            HttpServletRequest request){
        if( !password.equals(reTypePassword) ){
            throw new CommonException("비밀번호를 잘못 입력했습니다.");
        }

        authTokenService.validAndConfirmAndChangePassword(authTokenId, memberId, password);
        return "redirect:/confirm/success_reset_password";
    }

    @RequestMapping(value = "/confirm/success_reset_password", method = RequestMethod.GET)
    public String successResetPassword(Model model){
        logger.debug("모델 : {}", model);
        return "/confirm/success_reset_password";
    }

    @RequestMapping("/confirm/success_signup")
    public String confirmSuccessSignup(){
        return "/confirm/success_signup";
    }
}
