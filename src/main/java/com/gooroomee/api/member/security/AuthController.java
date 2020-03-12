package com.gooroomee.api.member.security;

import com.gooroomee.api.member.Member;
import com.gooroomee.api.member.MemberRepository;
import com.gooroomee.api.member.security.exception.CEmailSigninFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.internal.Errors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody SignUpDto signUpDto, Errors errors){
        try{
            authService.signup(signUpDto);
            return ResponseEntity.ok().build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody SignInDto signinDto){
        Member member=authService.signin(signinDto);
//        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
//        header.add("authToken", jwtTokenProvider.createToken(member.getUsername(), member.getRoles()));
//        return new ResponseEntity(header, HttpStatus.OK);
//        return new ResponseEntity(header, HttpStatus.OK);
        String token = jwtTokenProvider.createToken(String.valueOf(member.getUsername()), member.getRoles());
        log.info("token========================="+token);
        return ResponseEntity.ok().header("token", token).build();
    }

    @PutMapping("/dismembership/{member_id}")
    public ResponseEntity dismembership(@PathVariable Long member_id){
        try{
            authService.dismembership(member_id);
            return ResponseEntity.ok().build();
        }catch(IllegalStateException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CEmailSigninFailedException.class)
    protected ResponseEntity emailSigninFailed(HttpServletRequest request, CEmailSigninFailedException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("비밀번호가 정확하지 않습니다.");
    }
}
