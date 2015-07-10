package com.sungmook.social;

import com.sungmook.domain.SessionUser;
import com.sungmook.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
public class CustomSignInAdapter implements SignInAdapter {

    /**
     * 소셜 로그인 버튼을 눌렀을 때의 request 로 돌아가기 위한 RequestCache.
     */

    private final RequestCache requestCache;

    @Inject
    public CustomSignInAdapter(RequestCache requestCache) {
        this.requestCache = requestCache;
    }

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private TokenBasedRememberMeServices rememberMeServices;




    @Override
    public String signIn(String localUserId, Connection<?> connection, NativeWebRequest request) {
        SessionUser sessionUser =  (SessionUser)customUserDetailsService.loadUserByUserId( Long.valueOf(localUserId) );
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        SecurityContextHolder.getContext().setAuthentication( new UsernamePasswordAuthenticationToken( sessionUser, authentication.getCredentials(), sessionUser.getAuthorities() ) );
        rememberMeServices.onLoginSuccess(request.getNativeRequest(HttpServletRequest.class), request.getNativeResponse(HttpServletResponse.class), SecurityContextHolder.getContext().getAuthentication());
        return extractOriginalUrl(request);
    }

    private String extractOriginalUrl(NativeWebRequest request) {
        HttpServletRequest nativeReq = request.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse nativeRes = request.getNativeResponse(HttpServletResponse.class);
        SavedRequest saved = requestCache.getRequest(nativeReq, nativeRes);
        if (saved == null) {
            return null;
        }
        requestCache.removeRequest(nativeReq, nativeRes);
        removeAutheticationAttributes(nativeReq.getSession(false));
        return saved.getRedirectUrl();
    }

    private void removeAutheticationAttributes(HttpSession session) {
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
