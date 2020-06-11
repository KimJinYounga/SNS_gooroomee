package com.gooroomee.api.post.detail;

import com.gooroomee.api.post.Post;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class PostCreateResource extends EntityModel<Post> {
    public PostCreateResource(Post post, Link... links) {
        super(post, links);
    }
}
