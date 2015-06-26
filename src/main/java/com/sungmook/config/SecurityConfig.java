package com.sungmook.config;

import com.sungmook.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by Lim Sungmook(sungmook.lim@sk.com, ipes4579@gmail.com).
 */
@Configuration
@EnableWebMvcSecurity
@SuppressWarnings("unused")
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                .frameOptions().disable()                   // h2 console 에 접근하기 위해 열어둠
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .antMatchers("/users").hasRole("ADMIN")
//                .anyRequest().authenticated();
                .anyRequest().permitAll();
        http
                .formLogin()
                .failureUrl("/login?error")
                .defaultSuccessUrl("/")
                .loginPage("/login")
                .permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/")
                .permitAll();
    }

    @Configuration
    protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

        @Autowired
        private CustomUserDetailsService customUserDetailsService;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(customUserDetailsService);
        }
    }
}
