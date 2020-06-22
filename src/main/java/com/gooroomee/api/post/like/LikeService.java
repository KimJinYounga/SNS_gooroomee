package com.gooroomee.api.post.like;

import com.gooroomee.api.error.exception.PostNotFoundException;
import com.gooroomee.api.post.Post;
import com.gooroomee.api.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public List<LikeListDto> getLikes(Long post_id) {
        List<Like> likes = this.likeRepository.findAllByPost_PostId(post_id);

        List<LikeListDto> likeListDtos = new ArrayList<LikeListDto>();
        for(Like dto : likes) {
            likeListDtos.add(this.modelMapper.map(dto, LikeListDto.class));
        }
        return likeListDtos;
    }

    @Transactional
    public LikeListDto likePost(Long post_id, String visitorId) {
        Optional<Like> optionalLike = this.likeRepository.findByPost_PostIdAndEmail(post_id, visitorId);
        Post optionalPost = this.postRepository.findById(post_id).orElseThrow();
        if (optionalLike.isPresent()) {
            this.likeRepository.delete(optionalLike.get());
            return this.modelMapper.map(optionalLike.get(), LikeListDto.class);
        }
        else{
            Like like = Like.builder()
                    .email(visitorId)
                    .post(optionalPost)
                    .build();
            Like saved = this.likeRepository.save(like);
            return this.modelMapper.map(saved, LikeListDto.class);
        }

    }
}
