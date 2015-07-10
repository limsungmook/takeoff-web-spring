package com.sungmook.config;

import com.sungmook.domain.SessionUser;
import com.sungmook.social.CustomSignInAdapter;
import com.sungmook.social.UsersConnectionRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Configuration
@EnableSocial
public class SocialConfig implements SocialConfigurer {
    @Bean
    public SignInAdapter signInAdapter() {
        return new CustomSignInAdapter(new HttpSessionRequestCache());
    }

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
        cfConfig.addConnectionFactory(new FacebookConnectionFactory(env.getProperty("facebook.appKey"), env.getProperty("facebook.appSecret")));
    }

    /**
     * spring-social 에서는 기본적으로 JdbcUsersConnectionRepository 와 InMemory ... 만 제공해준다.
     * DB 에 상관없이 독립적인 애플리케이션을 만들고 싶다면 jpa 를 사용해 구현해야 하기 때문에 커스텀하게 만들었다.
     * @see JdbcUsersConnectionRepository
     *
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return new UsersConnectionRepositoryImpl();
    }

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

    @Bean
    @Scope(value="request", proxyMode= ScopedProxyMode.INTERFACES)
    public Facebook facebook(ConnectionRepository repository) {
        Connection<Facebook> connection = repository.findPrimaryConnection(Facebook.class);
        return connection != null ? connection.getApi() : null;
    }

    @Bean
    public ProviderSignInController providerSignInController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository) {
        return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, new CustomSignInAdapter(new HttpSessionRequestCache()));
    }
}