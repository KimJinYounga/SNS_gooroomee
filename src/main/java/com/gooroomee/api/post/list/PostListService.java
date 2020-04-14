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


    public Page<Post> getPostsList(String board_type, String filter, Pageable pageable) {
        Page<Post> page;
        if (filter == null) {
            page = this.postRepository.findAllByBoard_BoardType(board_type, pageable);
        }
        else {
            page = this.postRepository.findAllByBoard_BoardTypeAndEmail(board_type, filter, pageable);
        }
//        log.info("count Comments!! => "+commentsRepository.countAllByPost_PostId((long) 62));
        return page;
    }
}
