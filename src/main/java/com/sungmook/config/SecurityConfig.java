package com.sungmook.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Configuration
@EnableWebSecurity
@SuppressWarnings("unused")
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public RememberMeServices rememberMeServices(){
        return new TokenBasedRememberMeServices("takeoff-remember-me", userDetailsService);
    }

    @Bean
    public TextEncryptor textEncryptor() {
        return Encryptors.noOpText();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/css/**", "/**/*.png", "/**/*.gif", "/**/*.jpg", "/**/*.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .headers()
                .frameOptions().disable().and()                   // h2 console 에 접근하기 위해 열어둠
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .antMatchers("/connect").hasRole("USER")
                .antMatchers("/users", "/admin").hasRole("ADMIN")
                .anyRequest().permitAll();
        http
                .formLogin()
                .failureUrl("/auth/login?error")
                .loginProcessingUrl("/auth/login")
                .defaultSuccessUrl("/")
                .loginPage("/auth/login")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/auth/logout")
                .deleteCookies("takeoff-remember-me")
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .rememberMe()
                .rememberMeParameter("takeoff-remember-me")
                .tokenValiditySeconds(31536000);


    }

    @Configuration
    protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

        @Autowired
        private UserDetailsService userDetailsService;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService)
                    .passwordEncoder(new BCryptPasswordEncoder());
        }
    }
}
