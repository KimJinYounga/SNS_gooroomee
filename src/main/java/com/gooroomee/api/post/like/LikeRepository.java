package com.gooroomee.api.post.like;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findAllByPost_PostId(Long postId);
    Optional<Like> findByPost_PostIdAndEmail(Long postId, String email);
}
