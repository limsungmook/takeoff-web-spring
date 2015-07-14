package com.sungmook.controller.web;

import com.sungmook.domain.Story;
import com.sungmook.repository.StoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Controller
public class IndexController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StoryRepository storyRepository;

    @RequestMapping("/")
    public String index(Model model){

        Page<Story> pagedStories = storyRepository.findAll(new PageRequest(0, 20, Sort.Direction.DESC, "createdDate"));
        model.addAttribute("pagedStories", pagedStories);
        return "index";
    }
}
