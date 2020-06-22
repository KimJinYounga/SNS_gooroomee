package com.gooroomee.api.member.security;

import com.gooroomee.api.member.MemberRepository;
import com.gooroomee.api.member.security.exception.CUserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userPk){
        return memberRepository.findByEmail(userPk).orElseThrow(CUserNotFoundException::new);
    }
}
