package com.gooroomee.api.post.detail;

import com.gooroomee.api.post.Post;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PostDetailDto {
    private String title;
    private String content;
    private LocalDateTime reg_date;
    private String email;

    public Post toEntity(Post post){
        post.setTitle(this.title);
        post.setContent(this.content);
        post.setReg_date(this.reg_date);
        post.setEmail(this.email);
        return post;
    }
}
