package com.gooroomee.api.post.like;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooroomee.api.comment.CommentsDto;
import com.gooroomee.api.post.detail.PostDetailDto;
import com.gooroomee.api.post.detail.PostDetailResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/like")
@CrossOrigin(origins = "http://localhost:3000")
public class LikeController {

    private final LikeService likeService;
    @GetMapping("/list/{post_id}")
    public ResponseEntity getLikes(@PathVariable Long post_id) throws JsonProcessingException {
        List<LikeListDto> likeListDtos = this.likeService.getLikes(post_id);
        String list = new ObjectMapper().writeValueAsString(likeListDtos);
        System.out.println(list);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/{post_id}")
    public ResponseEntity LikePost(@PathVariable Long post_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        String visitorId = authentication.getName();
        System.out.println("visitorId -- "+ visitorId);
        try {
            LikeListDto likePost = this.likeService.likePost(post_id, visitorId);
            return ResponseEntity.ok(likePost);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
