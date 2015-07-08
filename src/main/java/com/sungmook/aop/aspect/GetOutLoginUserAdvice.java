package com.sungmook.aop.aspect;

import com.sungmook.aop.annotation.GetOutLoginUser;
import javassist.NotFoundException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */

@Aspect
@Component
public class GetOutLoginUserAdvice {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 로그인 한 사용자는 해당 어노테이션이 있는 메소드에 접근하지 못한다.
     * @param joinPoint
     * @param getOutLoginUser
     * @return
     * @throws Throwable
     */
    @Around(value = "@annotation(getOutLoginUser)")
    public Object methodArround(ProceedingJoinPoint joinPoint, GetOutLoginUser getOutLoginUser) throws Throwable {

        logger.debug("로그인 체크하기");
        if( SecurityContextHolder.getContext().getAuthentication() == null ){
            return joinPoint.proceed();
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal.getClass() == String.class) {
            return joinPoint.proceed();

        }

        throw new NotFoundException("이미 로그인 된 사용자입니다.");
    }

}
