package com.example.jwt.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jwt.config.auth.PrincipalDetails;
import com.example.jwt.dto.LoginRequestDto;
import com.example.jwt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

//스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있다.
// /login 요청해서 username, password 전송하면(post)
// UsernamePasswordAuthenticationFilter 동작을 한다.
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("JwtAuthenticationFilter: 로그인 시도중");

        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequestDto loginRequestDto = null;
        try {
            // 1. username, password를 받아서
            loginRequestDto = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);
            log.info("1. username, password를 받음={}", loginRequestDto);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 1-2. 인증 토큰을 생성 -> Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword());
        log.info("1-2. 인증 토큰을 생성={}", authenticationToken);

        // 2. 정상인지 로그인 시도를 해본다. authenticationManager 로 로그인 시도를 하면
        // PrincipalDetailsService가 호출 loadUserByUsername() 함수가 실행된다.
        // Authentication 객체에 로그인한 정보가 담긴다.
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        log.info("2. 정상인지 로그인 시도를 해본다, Authentication={}", authentication);

        // 3. PrincipalDetails를 세션에 담고 (권한 관리를 위해서 담는다)
        // Authentication 객체가 session 영역에 저장된다. -> 로그인이 되었다는 뜻이다.
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        log.info("3. PrincipalDetails를 세션에 담고, principalDetails.getUser().getUsername()={}", principalDetails.getUser().getUsername());

        // 4. JWT 토큰을 만들어서 응답해주면 된다.
        return authentication;
    }

    // attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행된다.
    // JWT 토큰을 만들어서 request 요청한 사용자에게 JWT토큰을 response해주면 된다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("successfulAuthentication 실행됨 : 인증 완료");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        // RSA 방식이 아닌 Hash암호화 방식
        String jwtToken = JWT.create()
                .withSubject("coke 토큰")
                .withExpiresAt(new Date(System.currentTimeMillis() + (60000 * 10))) //토큰 만료시간은 10분이 적당함
                .withClaim("id", principalDetails.getUser().getId())
                .withClaim("username", principalDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512("coke"));

        response.addHeader("Authorization", "Bearer " + jwtToken);
    }
}
