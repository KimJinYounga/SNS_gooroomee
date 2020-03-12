package com.gooroomee.api.member.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    @Value("spring")
    private String secretKey;
    private long tokenValidMilisecond=1000L*60*60;
    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init(){
        secretKey= Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
}
