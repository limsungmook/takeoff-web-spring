package com.sungmook.aop.annotation;


import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

/**
 * Spring Security 와의 Dependency 를 줄이기 위해 CurrentUser 어노테이션을 만들어 사용하라고 권고함.
 * 현재는 WebConfig 에 SessionUser 에 대한 리졸버를 작성함으로 묵시적으로 현재 세션 데이터를 바인딩하고 있다.
 * 어노테이션을 이용한 방법이 좋을지, 리졸버를 이용한 방식이 좋을지는 좀더 고민이 필요할 듯 하다.
 * http://docs.spring.io/spring-security/site/docs/4.0.1.RELEASE/reference/htmlsingle/#mvc-authentication-principal
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {}