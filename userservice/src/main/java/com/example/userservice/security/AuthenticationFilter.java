package com.example.userservice.security;

import com.example.userservice.vo.RequestLogin;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

// 이메일, 패스워드를 받아서 로그인 인증 작업 처리.
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    // InputStream 으로 데이터 받기 -> Request 변환 -> Email PW 추출 -> 인증 토큰 생성 -> 인증 처리
    @Override // Override 단축키: Ctrl + O
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try { // POST 메소드로 전달된 데이터는 Request 파라메터로 못 받으므로, InputStream 으로 받고 RequestLogin 으로 변환
            RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);

            // 이메일, 패스워드를 Spring Security 에서 쓸 수 있는 토큰으로 변환해서, 인증 처리 매니저에 넘김.
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>()
                    )
            );

        } catch(IOException e) { // IO 스트림 오류를 잡아주는 경우에 대해 명시해야 getInputStream() 오류가 안 뜬다.
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

    }
}
