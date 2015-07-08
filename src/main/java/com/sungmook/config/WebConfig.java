package com.sungmook.config;

import com.sungmook.domain.SessionUser;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(sessionUserArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

    /**
     * 파라미터에 SessionUser 가 존재하면 SecurityContext 를 뒤져 바인딩한다.
     * @return
     */
    @Bean
    public HandlerMethodArgumentResolver sessionUserArgumentResolver() {
        return new HandlerMethodArgumentResolver() {
            @Override
            public boolean supportsParameter(MethodParameter parameter) {
                return SessionUser.class.isAssignableFrom(parameter.getParameterType());
            }

            @Override
            public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
                if( !this.supportsParameter(parameter)) {
                    return WebArgumentResolver.UNRESOLVED;
                }
                if( SecurityContextHolder.getContext().getAuthentication() == null ){
                    return null;
                }
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                if (principal.getClass() == String.class) {
                    return null;
                }
                return principal;
            }
        };
    }

}
