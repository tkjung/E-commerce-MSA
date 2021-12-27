package com.example.userservice.security;

import com.example.userservice.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.boot.autoconfigure.aop.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Environment env;

    public WebSecurity(Environment env, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.env = env;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // 권한 관련 config
        http.csrf().disable();
        // http.authorizeRequests().antMatchers("/users/**").permitAll();
        http.authorizeRequests().antMatchers("/**") // 모든 코드에 대해서 통과시키지 않음.
                        .hasIpAddress("192.168.120.1") // IP 제한
                        .and()
                        .addFilter(getAuthenticationFilter()); // 이 필터를 거친 데이터에 한해서만 권한부여/작업진행.

        http.headers().frameOptions().disable();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception{    // 인증 처리
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(); // 인증처리하는 객체 생성
        authenticationFilter.setAuthenticationManager(authenticationManager()); // Spring Security에서 가져온 매니저

        return authenticationFilter;
    }

    // SELECT pwd FROM users WHERE email = ? -> 사용자 검색기능 = UserService 에서 받음.
    // DB에 있는 pwd(encrypted) == input_pwd(encrypted) 비교
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // 인증 관련 config
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);  // pwd 변환 작업, UserServiceApplication 에 있는 @Bean을 여기서 사용.
    }
}
