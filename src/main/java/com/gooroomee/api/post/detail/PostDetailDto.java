package com.gooroomee.api.post.detail;

import com.gooroomee.api.post.Post;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PostDetailDto {
    private String title;
    private String content;
    private String email;

    public Post toEntity(Post post){
        post.setTitle(this.title);
        post.setContent(this.content);
        post.setEmail(this.email);
        return post;
    }
}
