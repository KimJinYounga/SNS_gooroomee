package com.gooroomee.api.comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    Comments findCommentsByParent_CommentsId(Long id);
}
