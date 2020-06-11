package com.gooroomee.api.files.postfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostFileRepository extends JpaRepository<PostFile, String> {
    Optional<PostFile> findAllByPostId(Long postId);
    long countByPostId(Long postId);
}
