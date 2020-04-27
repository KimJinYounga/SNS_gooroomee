package com.gooroomee.api.post.detail;

import com.gooroomee.api.post.Post;
import com.gooroomee.api.post.like.Like;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PostDetailDto {
    private String title;
    private String content;
    private String email;
    private Long commentsLength;
    private Boolean isDeleted;
    private String fileId;
    private Long fileCnt;
    private List<Like> like;

    public Post toEntity(Post post){
        post.setTitle(this.title);
        post.setContent(this.content);
        post.setEmail(this.email);
        post.setCommentsLength(this.commentsLength);
        post.setIsDeleted(this.isDeleted);
        return post;
    }
}
