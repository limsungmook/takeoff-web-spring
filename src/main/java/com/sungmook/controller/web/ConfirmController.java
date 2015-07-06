package com.sungmook.controller.web;

import com.sungmook.aop.annotation.GetOutLoginUser;
import com.sungmook.domain.SignupMember;
import com.sungmook.helper.MessageHelper;
import com.sungmook.service.MemberService;
import javassist.tools.web.BadHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Controller
public class ConfirmController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/confirm")
    public String login() throws BadHttpRequest {

        return "/auth/login";
    }

}
