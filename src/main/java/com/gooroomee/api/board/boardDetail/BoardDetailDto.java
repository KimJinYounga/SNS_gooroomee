package com.gooroomee.api.board.boardDetail;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BoardDetailDto {
    private String title;
    private String contents;
    private Date date;
    private String email;
}
