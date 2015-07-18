package com.sungmook;


import com.sungmook.service.InitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@SpringBootApplication
public class Application {



    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "true");
        SpringApplication.run(Application.class, args);
    }

    // After Start

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private InitService initService;

    @PostConstruct
    @Transactional
    public void applicationInit(){
        initService.checkAndInitApplication();
    }

}
