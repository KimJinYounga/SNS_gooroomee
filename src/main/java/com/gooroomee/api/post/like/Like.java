package com.gooroomee.api.post.like;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.gooroomee.api.board.Board;
import com.gooroomee.api.post.Post;
import lombok.*;
import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of="likeId")
@Entity
@Table(name="t_like")
public class Like {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="likeId")
    private Long likeId;
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    @Setter
    private Post post;

}
