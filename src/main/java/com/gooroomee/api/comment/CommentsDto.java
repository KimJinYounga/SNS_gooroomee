package com.gooroomee.api.comment;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CommentsDto {
    private Long commentsId;
    private String comments;
    private Boolean isSecret;
    private List<CommentsDto> children;
    private String email;
    private Long postId;
    private Boolean isDeleted;
    private LocalDateTime createdAt;
}
