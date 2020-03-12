//package com.gooroomee.api.member;
//
//import com.gooroomee.api.member.security.AuthService;
//import com.gooroomee.api.member.security.SignInDto;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.modelmapper.internal.Errors;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@Slf4j
//@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
//@RequestMapping("/auth")
//public class MemberController {
//
//    private final AuthService authService;
//
//    @PostMapping("/signup")
//    public ResponseEntity signup(@RequestBody Member member, Errors errors){
//        try{
//            authService.signup(member);
//            return ResponseEntity.ok().build();
//        }catch(IllegalArgumentException e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @PostMapping("/signin")
//    public ResponseEntity signin(@RequestBody SignInDto signinDto){
//        if(authService.signin(signinDto)){
//            return ResponseEntity.ok().build();
//        }
//        else{
//            return ResponseEntity.badRequest().body("비번불일치");
//        }
//    }
//
//    @PutMapping("/dismembership/{member_id}")
//    public ResponseEntity dismembership(@PathVariable Long member_id){
//        try{
//            authService.dismembership(member_id);
//            return ResponseEntity.ok().build();
//        }catch(IllegalStateException e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//}
