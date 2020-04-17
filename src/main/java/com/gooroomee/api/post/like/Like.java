package com.gooroomee.api.post.like;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of="likeId", callSuper = false)
@Entity
@Table(name="t_like")
public class Like {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="likeId")
    private Long likeId;
    private String email;
    private Long postId;
}
