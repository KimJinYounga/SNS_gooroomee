package com.gooroomee.api.member.security;

import com.gooroomee.api.error.exception.MemberNotFoundException;
import com.gooroomee.api.member.Member;
import com.gooroomee.api.member.MemberRepository;
import com.gooroomee.api.member.security.exception.CEmailSigninFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignUpDto signUpDto){
        if (memberRepository.findByEmail(signUpDto.getEmail()).isEmpty()){
            Member member = Member.builder()
                    .email(signUpDto.getEmail())
                    .name(signUpDto.getName())
                    .password(passwordEncoder.encode(signUpDto.getPassword()))
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
            memberRepository.save(member);
        }
    }

    @Transactional
    public Member signin(SignInDto signinDto){
        Member member = memberRepository.findByEmail(signinDto.getEmail()).orElseThrow(MemberNotFoundException::new);
        if(!passwordEncoder.matches(signinDto.getPassword(), member.getPassword())){
            throw new CEmailSigninFailedException();
        }
        return member;

    }

    @Transactional
    public void dismembership(Long member_id){
        Member member = memberRepository.findById(member_id).orElseThrow(MemberNotFoundException::new);
        member.setEmail("");
        member.setName("");
        member.setPassword("");
        memberRepository.save(member);
    }
}
