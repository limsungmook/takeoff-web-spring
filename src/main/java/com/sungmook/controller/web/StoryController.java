package com.sungmook.controller.web;

import com.sungmook.domain.Story;
import com.sungmook.service.StoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Controller
public class StoryController{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StoryService storyService;

    @RequestMapping(path = "/story/write", method = RequestMethod.GET)
    public String form(Story story){
        return "/story/write";
    }

    @RequestMapping(path = "/story/write", method = RequestMethod.POST)
    public String write(Story story){
        storyService.save(story);
        return "redirect:/";
    }


}
