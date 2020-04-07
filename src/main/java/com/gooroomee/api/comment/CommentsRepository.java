package com.gooroomee.api.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findCommentsByParentAndPost_PostId(Long parentId, Long id);
    Long countAllByPost_PostId(Long postId);

    Optional<Comments> findByCommentsIdAndMember_Email(Long comments_id, String visitorId);
}
