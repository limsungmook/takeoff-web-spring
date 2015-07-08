package com.sungmook.controller.web;

import com.sungmook.domain.AuthToken;
import com.sungmook.exception.InvalidTokenException;
import com.sungmook.service.AuthTokenService;
import javassist.tools.web.BadHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Controller
public class ConfirmController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthTokenService authTokenService;

    @RequestMapping("/confirm")
    public String confirm(@RequestParam(value = "mid", required = true) Long memberId, @RequestParam(value = "token", required = true) String token) throws BadHttpRequest {

        AuthToken authToken = null;

        try {
            authToken = authTokenService.confirmAndGet(memberId, token);
        }catch( InvalidTokenException ex ){
            return "/confirm/invalid";
        }

        if( authToken.getType() == AuthToken.Type.SIGNUP ){
            return "redirect:/confirm/success_signup";
        }
        return "redirect:/confirm/success_signup";
    }

    @RequestMapping("/confirm/success_signup")
    public String confirmSuccessSignup(){
        return "/confirm/success_signup";
    }
}
