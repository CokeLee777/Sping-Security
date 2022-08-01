package io.security.corespingsecurity.security.provider;

import io.security.corespingsecurity.security.common.FormWebAuthenticationDetails;
import io.security.corespingsecurity.security.service.AccountContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        AccountContext accountContext = (AccountContext) userDetailsService.loadUserByUsername(username);

        //비밀번호가 일치하지 않는다면
        if(!passwordEncoder.matches(password, accountContext.getAccount().getPassword())){
            throw new BadCredentialsException("BadCredentialsException");
        }

        FormWebAuthenticationDetails formWebAuthenticationDetails = (FormWebAuthenticationDetails) authentication.getDetails();
        String secretKey = formWebAuthenticationDetails.getSecretKey();
        if(secretKey == null || !"secret".equals(secretKey)){
            throw new InsufficientAuthenticationException("InsufficientAuthenticationException");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(accountContext.getAccount(), null, accountContext.getAuthorities());

        //인증이 완료된 authentication 객체를 AuthenticationManager 에게 반환
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 토큰값이 authentication 클래스 타입과 일치할 때, 발동하도록
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
