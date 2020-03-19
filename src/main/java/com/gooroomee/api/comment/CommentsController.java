package com.gooroomee.api.comment;

import com.gooroomee.api.post.detail.PostDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/comments")
public class CommentsController {
    private final CommentsService commentsService;
    private final PostDetailService postDetailService;

    @PostMapping("/{post_id}")
    public ResponseEntity storeComments(@PathVariable Long post_id,
                                        @RequestParam(value = "parentId", required = false) Long parentId,
                                        @RequestBody @Valid CommentsDto commentsDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        String visitorId = authentication.getName();
        this.commentsService.storeComments(visitorId, post_id, commentsDto, parentId);
        return ResponseEntity.ok().build();
    }

}
