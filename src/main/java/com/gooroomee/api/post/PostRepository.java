package com.gooroomee.api.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByBoard_BoardTypeAndEmail(String board_type, String email, Pageable pageable);
    Page<Post> findAllByBoard_BoardType(String board_type, Pageable pageable);
    Optional<Post> findByPostIdAndMember_Email(Long id, String email);
    Post findPostByPostId(Long postId);
}
