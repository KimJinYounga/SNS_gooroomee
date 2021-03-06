package com.gooroomee.api.post.list;

import com.gooroomee.api.files.profileImage.ProfileImageRepository;
import com.gooroomee.api.files.profileImage.ProfileImageService;
import com.gooroomee.api.post.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost:3000")
public class PostListController {
    private final PostListService postListService;
    private final ProfileImageRepository profileImageRepository;
    /*
    게시판 전체 리스트 검색
     */
    @GetMapping("/{board_type}")
    public ResponseEntity getPostsList(@PathVariable(name = "board_type") String board_type,
                                       @RequestParam(value = "filter", required = false) String filter,
            Pageable pageable, PagedResourcesAssembler<Post> assembler) {
        Page<Post> page = this.postListService.getPostsList(board_type,filter,pageable);
        PagedModel<PostListResource> pagedResources = assembler.toModel(page, e -> new PostListResource(e, profileImageRepository));
        pagedResources.add(linkTo(PostListController.class).withRel("posts-list"));
        return ResponseEntity.ok(pagedResources);
    }

    @GetMapping("/profile/{email}")
    public ResponseEntity getMemberPostsList(@PathVariable(name = "email") String email,
                                       Pageable pageable, PagedResourcesAssembler<Post> assembler) {
        Page<Post> page = this.postListService.getMemberPostsList(email,pageable);
        PagedModel<PostListResource> pagedResources = assembler.toModel(page, e -> new PostListResource(e, profileImageRepository));
        pagedResources.add(linkTo(PostListController.class).withRel("posts-list"));
        return ResponseEntity.ok(pagedResources);
    }
}