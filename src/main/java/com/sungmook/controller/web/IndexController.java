package com.sungmook.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(Map<String, Object> model){
        model.put("time", new Date());
        model.put("message", "Hello, Spring boot~!");
        return "index";
    }
}
