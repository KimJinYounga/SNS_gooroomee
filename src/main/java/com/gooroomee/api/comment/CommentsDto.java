package com.gooroomee.api.comment;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CommentsDto {
    private String comments;
    private Boolean isSecret;
    private List<CommentsDto> children;
}
