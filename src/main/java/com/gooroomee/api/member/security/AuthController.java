package com.gooroomee.api.member.security;

import com.gooroomee.api.member.security.exception.CEmailSigninFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/auth")
public class AuthController {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CEmailSigninFailedException.class)
    protected ResponseEntity emailSigninFailed(HttpServletRequest request, CEmailSigninFailedException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("계정이 존재하지 않거나 이메일 또는 비밀번호가 정확하지 않습니다.");
    }
}
