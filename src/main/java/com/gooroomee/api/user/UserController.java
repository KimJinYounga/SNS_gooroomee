package com.gooroomee.api.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.internal.Errors;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody User user, Errors errors) {
        if(userService.signup(user))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.badRequest().body("아이디중복");
    }

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody SignInDto signinDto) {
        if(userService.signin(signinDto))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.notFound().build();
    }
}
