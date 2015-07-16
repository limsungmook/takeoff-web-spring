package com.sungmook.controller.web;

import com.sungmook.domain.Comment;
import com.sungmook.domain.Story;
import com.sungmook.domain.User;
import com.sungmook.repository.CommentRepository;
import com.sungmook.repository.UserRepository;
import com.sungmook.security.SessionUser;
import com.sungmook.service.CommentService;
import com.sungmook.service.StoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "/story/write", method = RequestMethod.GET)
    public String form(SessionUser sessionUser, Story story, Model model){
        User user = userRepository.findById(sessionUser.getUserId());
        model.addAttribute(story);
        model.addAttribute("scopes", user.getScopeList());
        return "/story/write";
    }

    @RequestMapping(path = "/story/write", method = RequestMethod.POST)
    public String write(Story story){
        storyService.save(story);
        return "redirect:/";
    }

    @RequestMapping(path = "/story/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, Model model, Comment comment){
        model.addAttribute("story", storyService.readAndFindById(id));

        Page<Comment> pagedComments= commentRepository.findAll(new PageRequest(0, 20, Sort.Direction.DESC, "createdDate"));
        model.addAttribute("pagedComments", pagedComments);
        return "/story/view";
    }

    @RequestMapping(path = "/story/{id}/comment", method = RequestMethod.POST)
    public String writeComment(@PathVariable Long id, Comment comment, Model model){
        commentService.save(comment);
        return "redirect:/story/" + id;
    }

}
