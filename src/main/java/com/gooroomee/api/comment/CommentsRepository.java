package com.gooroomee.api.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findCommentsByParentAndPost_PostId(Long parentId, Long id);
}
