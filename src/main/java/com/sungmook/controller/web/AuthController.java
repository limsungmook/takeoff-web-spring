package com.sungmook.controller.web;

import com.sungmook.aop.annotation.GetOutLoginUser;
import com.sungmook.domain.SignupMember;
import com.sungmook.helper.MessageHelper;
import javassist.tools.web.BadHttpRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Controller
public class AuthController {

    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private MessageHelper messageHelper;


    @GetOutLoginUser
    @RequestMapping("/auth/login")
    public String login() throws BadHttpRequest {

        return "auth/login";
    }

    @RequestMapping(name="/auth/signup", method = RequestMethod.GET)
    public String signup() throws BadHttpRequest {

        return "auth/signup";
    }

    @RequestMapping(name="/auth/signup", method = RequestMethod.POST)
    public String signup(@ModelAttribute @Valid SignupMember signupMember, BindingResult bindingResult) throws BadHttpRequest {

        if( bindingResult.hasErrors() ){
            logger.debug("오류 개수 : " + bindingResult.getAllErrors().size());
            for( ObjectError error : bindingResult.getAllErrors() ){
                logger.debug("tostring:" + error.toString());
                logger.debug(error.getDefaultMessage());
            }

        }
        logger.debug(signupMember.toString());
        return "auth/signup";
    }
}
