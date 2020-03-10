package com.gooroomee.api.board;

import com.gooroomee.api.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
//@Setter
@EqualsAndHashCode(of="boardId")
@Entity
@Table(name="board")
public class Board {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="boardId")
    private Long boardId;

    @Column(name="title")
    @Setter
    private String title;

    @Column(name="content")
    @Setter
    private String content;

    @Column(name="reg_date")
    @Setter
    private LocalDateTime reg_date;

    @Column(name="email")
    @Setter
    private String email;

    @ManyToOne
    @JoinColumn(name = "memberId")
    @Setter
    private Member member;

}
