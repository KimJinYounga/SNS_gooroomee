package com.gooroomee.api.comment;

import com.gooroomee.api.error.exception.CommentsNotFoundException;
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
import javax.xml.stream.events.Comment;
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
    public Comments storeComments(String visitor_id, Long post_id, CommentsDto commentsDto, Long parentId) {
        Member member = memberRepository.findByEmail(visitor_id).orElseThrow(MemberNotFoundException::new);
        Post post = postRepository.findById(post_id).orElseThrow(PostNotFoundException::new);
        post.setCommentsLength(commentsRepository.countAllByPost_PostId(post_id)+1);
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

        Comments comment = this.commentsRepository.save(comments);
        return comment;
    }

    public List<CommentsDto> getComments(Long post_id) {
        List<Comments> comments = commentsRepository.findCommentsByParentAndPost_PostId(null, post_id);

        List<CommentsDto> commentsDtos = new ArrayList<CommentsDto>();
        for (Comments dto : comments) {
            commentsDtos.add(this.modelMapper.map(dto, CommentsDto.class));
        }
        return commentsDtos;
    }

    @Transactional
    public Comments deleteComment(Long comments_id, String visitorId) {
        Comments comments = this.commentsRepository.findByCommentsIdAndMember_Email(comments_id, visitorId).orElseThrow(CommentsNotFoundException::new);
        comments.setIsDeleted(Boolean.TRUE);
        return this.commentsRepository.save(comments);
    }
}