package com.gooroomee.api.board.detail;

import com.gooroomee.api.board.Board;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BoardDetailDto {
    private String title;
    private String content;
    private LocalDateTime reg_date;
    private String email;

    public Board toEntity(Board board){
        board.setTitle(this.title);
        board.setContent(this.content);
        board.setReg_date(this.reg_date);
        board.setEmail(this.email);
        return board;
    }
}
