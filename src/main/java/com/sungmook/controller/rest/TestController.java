package com.sungmook.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@RestController
public class TestController {
    @RequestMapping("/")
    public String index(){
        return "Hello world!";
    }

}
