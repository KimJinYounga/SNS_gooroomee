package com.gooroomee.api.post.list;

import com.gooroomee.api.post.Post;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class PostListResource extends EntityModel<Post> {
    public PostListResource(Post post, Link... links) {
        super(post, links);
        add(new Link("/post/"+post.getPost_id()).withSelfRel());
    }
}
