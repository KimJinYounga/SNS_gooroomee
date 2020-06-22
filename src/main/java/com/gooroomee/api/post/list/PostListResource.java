package com.gooroomee.api.post.list;

import com.gooroomee.api.files.exception.MyFileNotFoundException;
import com.gooroomee.api.files.profileImage.ProfileImage;
import com.gooroomee.api.files.profileImage.ProfileImageRepository;
import com.gooroomee.api.files.profileImage.ProfileImageService;
import com.gooroomee.api.post.Post;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import java.util.Optional;

public class PostListResource extends EntityModel<Post> {

    public PostListResource(Post post, ProfileImageRepository profileImageRepository, Link... links) {
        super(post, links);
        add(new Link("/post/"+post.getPostId()).withSelfRel());
        Optional<ProfileImage> fileByEmail = profileImageRepository.findAllByEmail(post.getEmail());
        if(fileByEmail.isPresent()) {
            add(new Link("/profileImage/"+ fileByEmail.get().getFile_id()).withRel("profileImage"));
        }
    }
}
