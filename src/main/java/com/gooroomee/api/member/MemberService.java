package com.gooroomee.api.member;

import com.gooroomee.api.error.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService{
    private final MemberRepository memberRepository;

    @Transactional
    public void signup(Member member){
        if (memberRepository.findByEmail(member.getEmail()).isEmpty()){
            memberRepository.save(member);
        }
    }

    @Transactional
    public boolean signin(SignInDto signinDto){
        Member findMember = memberRepository.findByEmail(signinDto.getEmail()).orElseThrow(MemberNotFoundException::new);
        if(findMember.getPassword().equals(signinDto.getPassword())){
            return true;
        }
        return false;
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
