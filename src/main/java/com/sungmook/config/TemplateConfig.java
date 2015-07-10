package com.sungmook.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.templateresolver.TemplateResolver;

import javax.annotation.PostConstruct;
import java.util.HashSet;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Configuration
public class TemplateConfig {
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());


    /**
     * /templates 경로를 바꾸고 싶다면 TemplateResolver Bean 설정
     */
    @Autowired
    private TemplateResolver templateResolver;

    @Autowired
    private TemplateEngine templateEngine;

    @PostConstruct
    public void init(){
        HashSet sets = new HashSet();
        sets.add(new SpringSecurityDialect());
        templateEngine.setAdditionalDialects(sets);
    }

}
