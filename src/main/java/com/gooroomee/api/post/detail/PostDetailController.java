package com.gooroomee.api.post.detail;

import com.gooroomee.api.board.BoardRepository;
import com.gooroomee.api.error.exception.BoardNotFoundException;
import com.gooroomee.api.files.profileImage.ProfileImage;
import com.gooroomee.api.files.profileImage.ProfileImageRepository;
import com.gooroomee.api.post.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/post")
public class PostDetailController {
    private final PostDetailService postDetailService;
    private final ProfileImageRepository profileImageRepository;
    private final BoardRepository boardRepository;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String responseFail2(
            @RequestParam(required = false) String value1,
            @RequestParam(required = false) String value2) {
        if (value1 == null || value1.equals("")) {
            throw new IllegalArgumentException("value is null");
        }
        return "fail";
    }

    @GetMapping(value = "/{post_id}")
    public ResponseEntity getPostDetail(@PathVariable(name = "post_id") Long post_id) {
        PostDetailDto post = postDetailService.getPost(post_id);
        PostDetailResource postResource = new PostDetailResource(post, post_id);
        postResource.add(linkTo(PostDetailController.class).slash(post_id).withRel("updatePost"));
        postResource.add(linkTo(PostDetailController.class).slash(post_id).withRel("deletePost"));
        return ResponseEntity.ok(postResource);
    }

    @PostMapping("/{board_type}")
    public ResponseEntity createPost(@RequestBody @Valid PostDetailDto postDetailDto,
                                     @PathVariable(name = "board_type") String boardType,
                                     Errors errors) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null)
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        try {
            Post post = this.postDetailService.storePost(postDetailDto, boardType);
            PostCreateResource postCreateResource = new PostCreateResource(post);
            Optional<ProfileImage> fileByEmail = profileImageRepository.findAllByEmail(post.getEmail());
            if(fileByEmail.isPresent()) {
                postCreateResource.add(new Link("/profileImage/"+fileByEmail.get().getFile_id()).withRel("profileImage"));
            }
            return ResponseEntity.ok().body(postCreateResource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{post_id}")
    public ResponseEntity updatePost(@PathVariable Long post_id,
                                     @RequestBody @Valid PostDetailDto postDetailDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        try {
            PostDetailDto updatedPost = this.postDetailService.updatePost(post_id, postDetailDto);
            PostDetailResource postDetailResource = new PostDetailResource(updatedPost, post_id);
            return ResponseEntity.ok(postDetailResource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity deletePost(@PathVariable Long post_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        String visitorId = authentication.getName();
        try {
            Post post = this.postDetailService.deletePost(post_id, visitorId);
            return ResponseEntity.ok(post);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

    }
}
