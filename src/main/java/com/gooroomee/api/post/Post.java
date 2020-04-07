package com.gooroomee.api.post;

import com.gooroomee.api.board.Board;
import com.gooroomee.api.common.CommonDateEntity;
import com.gooroomee.api.member.Member;
import lombok.*;
import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of="postId", callSuper = false)
@Entity
@Table(name="t_post")
public class Post extends CommonDateEntity {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long postId;

    @Column(name="title", nullable = false, length=100)
    @Setter
    private String title;

    @Column(name="content", length = 500)
    @Setter
    private String content;


    @Column(name="email", length = 50)
    @Setter
    private String email;

    @Column(name="commentsLength", length = 50)
    @Setter
    private Long commentsLength;

    @Builder.Default
    @Column(name="isDeleted", length = 10)
    @Setter
    private Boolean isDeleted = Boolean.FALSE;

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
