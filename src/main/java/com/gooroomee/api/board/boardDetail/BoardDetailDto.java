package com.gooroomee.api.board;

import com.gooroomee.api.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
public class BoardDetailDto {
    private String title;
    private String contents;
    private Date date;
    private String email;
}
