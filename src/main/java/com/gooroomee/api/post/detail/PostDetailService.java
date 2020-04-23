package com.gooroomee.api.post.detail;

import com.gooroomee.api.board.Board;
import com.gooroomee.api.board.BoardRepository;
import com.gooroomee.api.error.exception.BoardNotFoundException;
import com.gooroomee.api.error.exception.MemberNotFoundException;
import com.gooroomee.api.error.exception.PostNotFoundException;
import com.gooroomee.api.files.exception.MyFileNotFoundException;
import com.gooroomee.api.files.postfile.PostFile;
import com.gooroomee.api.files.postfile.PostFileRepository;
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

    private final PostFileRepository postFileRepository;

    private final ModelMapper modelMapper;

    @Transactional
    public PostDetailDto getPost(Long post_id) {
        Post post = this.postRepository.findById(post_id).orElseThrow(PostNotFoundException::new);
        PostDetailDto postDetailDto = this.modelMapper.map(post, PostDetailDto.class);
        return postDetailDto;
    }

    @Transactional
    public Post storePost(PostDetailDto postDetailDto, String boardType) {
        Board board = this.boardRepository.findByBoardType(boardType).orElseThrow(BoardNotFoundException::new);
        Post post = new Post();
        postDetailDto.toEntity(post);
        post.setMember(this.memberRepository.findMemberByEmail(postDetailDto.getEmail()).orElseThrow(MemberNotFoundException::new));
        post.setBoard(board);
        post.setCommentsLength((long) 0);
        post.setIsDeleted(Boolean.FALSE);
        Post savePost = this.postRepository.save(post);

        if (postDetailDto.getFileId() == null) {
            return savePost;
        }
        PostFile file = this.postFileRepository.findById(postDetailDto.getFileId()).orElseThrow(() -> new MyFileNotFoundException("파일을 찾을 수 없습니다."));
        file.setPostId(post.getPostId());
        this.postFileRepository.save(file);
        savePost.setFileCnt(this.postFileRepository.countByPostId(savePost.getPostId()));
        Post savedPost = this.postRepository.save(savePost);
        return savedPost;
    }

    @Transactional
    public PostDetailDto updatePost(Long post_id, PostDetailDto postDetailDto) {
        Post post = this.postRepository.findById(post_id).orElseThrow(PostNotFoundException::new);
        postDetailDto.toEntity(post);
        this.postRepository.save(post);

        if (postDetailDto.getFileId() == null) {
            return this.modelMapper.map(post, PostDetailDto.class);
        }
        else {
            PostFile file = this.postFileRepository.findById(postDetailDto.getFileId()).orElseThrow();
            file.setPostId(post.getPostId());
            this.postFileRepository.save(file);
        }
        post.setFileCnt(this.postFileRepository.countByPostId(post.getPostId()));
        this.postRepository.save(post);

        PostDetailDto returnValue = this.modelMapper.map(post, PostDetailDto.class);

        return returnValue;
    }

    @Transactional
    public Post deletePost(Long post_id, String visitorId) {
        Post post = this.postRepository.findByPostIdAndMember_Email(post_id, visitorId).orElseThrow(PostNotFoundException::new);
        post.setIsDeleted(Boolean.TRUE);
        return this.postRepository.save(post);
    }
}
