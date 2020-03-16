package com.gooroomee.api.post;

import com.gooroomee.api.board.Board;
import com.gooroomee.api.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of="post_id")
@Entity
@Table(name="t_post")
public class Post {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long post_id;

    @Column(name="title", nullable = false, length=100)
    @Setter
    private String title;

    @Column(name="content", length = 500)
    @Setter
    private String content;

    @Column(name="reg_date")
    @Setter
    private LocalDateTime reg_date;

    @Column(name="email", length = 50)
    @Setter
    private String email;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @Setter
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    @Setter
    private Board board;

    protected Board getBoard(){
        return board;
    }
    public Post setUpdate(String title, String content){
        this.title=title;
        this.content=content;
        return this;
    }

}
