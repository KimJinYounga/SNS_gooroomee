package com.gooroomee.api.boardList;

import com.gooroomee.api.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@EqualsAndHashCode(of="user_id")
@Entity
@Table(name="BoardList")
public class BoardList {
    @Id
    private long boardId;
    private String title;
    private String contents;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
