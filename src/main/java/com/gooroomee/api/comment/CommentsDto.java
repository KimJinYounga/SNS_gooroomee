package com.gooroomee.api.comment;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CommentsDto {
    private String comments;
    private Boolean isSecret;

}
