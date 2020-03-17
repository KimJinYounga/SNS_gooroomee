package com.gooroomee.api.post.list;

import com.gooroomee.api.post.Post;
import com.gooroomee.api.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostListService {
    private final PostRepository postRepository;


    public Page<Post> getPostsList(String board_type, Pageable pageable) {
        Page<Post> page = this.postRepository.findAllByBoard_BoardType(board_type, pageable);
        return page;
    }
}
