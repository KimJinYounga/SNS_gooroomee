package com.gooroomee.api.post.detail;

import com.gooroomee.api.board.Board;
import com.gooroomee.api.board.BoardRepository;
import com.gooroomee.api.board.BoardType;
import com.gooroomee.api.error.exception.BoardNotFoundException;
import com.gooroomee.api.error.exception.PostNotFoundException;
import com.gooroomee.api.error.exception.MemberNotFoundException;
import com.gooroomee.api.member.MemberRepository;
import com.gooroomee.api.post.Post;
import com.gooroomee.api.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostDetailService {
    private final PostRepository postRepository;

    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;

    private final ModelMapper modelMapper;

    @Transactional
    public PostDetailDto getPost(Long post_id) {
        Post post = this.postRepository.findById(post_id).orElseThrow(PostNotFoundException::new);
        PostDetailDto postDetailDto = this.modelMapper.map(post, PostDetailDto.class);
        return postDetailDto;
    }

    @Transactional
    public void storePost(PostDetailDto postDetailDto, String boardType) {
        Board board = this.boardRepository.findByBoardType(boardType).orElseThrow(BoardNotFoundException::new);
        Post post = new Post();
        postDetailDto.toEntity(post);
        post.setMember(this.memberRepository.findMemberByEmail(postDetailDto.getEmail()).orElseThrow(MemberNotFoundException::new));
        post.setBoard(board);
        this.postRepository.save(post);
    }

    @Transactional
    public PostDetailDto updatePost(Long post_id, PostDetailDto postDetailDto) {
        Post post = this.postRepository.findById(post_id).orElseThrow(PostNotFoundException::new);
        postDetailDto.toEntity(post);
        this.postRepository.save(post);
        PostDetailDto returnValue = this.modelMapper.map(post, PostDetailDto.class);
        return returnValue;
    }

    @Transactional
    public void deletePost(Long post_id) {
        this.postRepository.findById(post_id).orElseThrow(PostNotFoundException::new);
        this.postRepository.deleteById(post_id);
    }
}