package com.gooroomee.api.comment;

import com.gooroomee.api.error.exception.MemberNotFoundException;
import com.gooroomee.api.error.exception.PostNotFoundException;
import com.gooroomee.api.member.Member;
import com.gooroomee.api.member.MemberRepository;
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
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void storeComments(String visitor_id, Long post_id, CommentsDto commentsDto, Long parentId) {
        Member member = memberRepository.findByEmail(visitor_id).orElseThrow(MemberNotFoundException::new);
        Post post = postRepository.findById(post_id).orElseThrow(PostNotFoundException::new);

        Comments comments = Comments.builder()
                .member(member)
                .post(post)
                .email(member.getEmail())
                .comments(commentsDto.getComments())
                .parent(null)
                .isSecret(commentsDto.getIsSecret())
                .build();

        if (parentId != null) {
            Optional<Comments> parent = commentsRepository.findById(parentId);
            comments.setParent(parent.get());
        }

        this.commentsRepository.save(comments);
    }

    public List<CommentsDto> getComments(Long post_id) {
        List<Comments> comments = commentsRepository.findCommentsByParentAndPost_PostId(null, post_id);

        List<CommentsDto> commentsDtos = new ArrayList<CommentsDto>();
        for (Comments dto : comments) {
            commentsDtos.add(this.modelMapper.map(dto, CommentsDto.class));
        }
        return commentsDtos;
    }
}


