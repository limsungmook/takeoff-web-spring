package com.sungmook.config;

import com.sungmook.domain.SessionUser;
import com.sungmook.social.CustomSignInAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Configuration
@SuppressWarnings("unused")
public class SocialConfig  {


    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    @Bean
    public SignInAdapter signInAdapter() {
        return new CustomSignInAdapter(new HttpSessionRequestCache());
    }


    @Bean
    public ProviderSignInController providerSignInController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository) {
        return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, signInAdapter());
    }


//
//    @Bean
//    @Scope(value="request", proxyMode= ScopedProxyMode.INTERFACES)
//    public Facebook facebook(ConnectionRepository repository) {
//        Connection<Facebook> connection = repository.findPrimaryConnection(Facebook.class);
//        return connection != null ? connection.getApi() : null;
//    }

    @Configuration
    protected static class UserIdSourceAdapter extends SocialConfigurerAdapter {

        @Override
        public UserIdSource getUserIdSource() {
            return new UserIdSource() {
                @Override
                public String getUserId() {

                    if( SecurityContextHolder.getContext().getAuthentication() == null ){
                        return null;
                    }
                    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                    if (principal.getClass() == String.class) {
                        return null;
                    }
                    SessionUser sessionUser = (SessionUser) principal;
                    return String.valueOf(sessionUser.getMemberId());
                }
            };
        }
    }
}