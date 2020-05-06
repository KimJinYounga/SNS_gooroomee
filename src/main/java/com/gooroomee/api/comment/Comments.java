package com.gooroomee.api.comment;

import com.gooroomee.api.common.CommonDateEntity;
import com.gooroomee.api.member.Member;
import com.gooroomee.api.post.Post;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "commentsId", callSuper = false)
@Entity
@Table(name = "t_comment")
public class Comments extends CommonDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comments_id")
    private Long commentsId;
    @Setter
    private String email;
    @Setter
    private String comments;
    @Setter
    private Boolean isDeleted = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "parents_id")
    private Comments parent;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent", orphanRemoval = true)
    private List<Comments> children = new ArrayList<Comments>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    @Setter
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @Setter
    private Post post;

    public void setParent(Comments parent) {
        this.parent = parent;
        if (!parent.getChildren().contains(this)) {
            parent.getChildren().add(this);
        }
    }

}
