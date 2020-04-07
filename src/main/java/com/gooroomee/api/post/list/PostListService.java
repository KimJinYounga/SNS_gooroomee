package com.gooroomee.api.post.list;

import com.gooroomee.api.comment.CommentsRepository;
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
    private final CommentsRepository commentsRepository;


    public Page<Post> getPostsList(String board_type, Pageable pageable) {
        Page<Post> page = this.postRepository.findAllByBoard_BoardType(board_type, pageable);
//        log.info("count Comments!! => "+commentsRepository.countAllByPost_PostId((long) 62));
        return page;
    }
}
