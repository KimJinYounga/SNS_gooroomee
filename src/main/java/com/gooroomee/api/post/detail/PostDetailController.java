package com.gooroomee.api.post.detail;

import com.gooroomee.api.board.BoardRepository;
import com.gooroomee.api.board.BoardType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/post")
public class PostDetailController {
    private final PostDetailService postDetailService;

    private final BoardRepository boardRepository;

    @GetMapping(value = "/{post_id}")
    public ResponseEntity getPostDetail(@PathVariable(name = "post_id") Long post_id){
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
        try{
            this.postDetailService.storePost(postDetailDto, boardType);
            return ResponseEntity.ok().build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body("게시판등록실패");
        }
    }

    @PutMapping("/{post_id}")
    public ResponseEntity updatePost(@PathVariable Long post_id,
                                      @RequestBody @Valid PostDetailDto postDetailDto) {
        try{
            PostDetailDto updatedPost = this.postDetailService.updatePost(post_id, postDetailDto);
            PostDetailResource postDetailResource = new PostDetailResource(updatedPost, post_id);
            return ResponseEntity.ok(postDetailResource);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity deletePost(@PathVariable Long post_id) {
        try{
            this.postDetailService.deletePost(post_id);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }

    }
}
