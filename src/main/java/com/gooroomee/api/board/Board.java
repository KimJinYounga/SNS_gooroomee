package com.gooroomee.api.board;

import com.gooroomee.api.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="boardId")
@Entity
@Table(name="board")
public class Board {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="boardId")
    private long boardId;

    @Column(name="title")
    private String title;

    @Column(name="contents")
    private String contents;

    @Column(name="date")
    private Date date;

    @Column(name="email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

}
