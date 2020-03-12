package com.gooroomee.api.board.detail;

import com.gooroomee.api.board.Board;
import com.gooroomee.api.board.BoardRepository;
import com.gooroomee.api.error.exception.BoardNotFoundException;
import com.gooroomee.api.error.exception.MemberNotFoundException;
import com.gooroomee.api.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardDetailService {
    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;

    private final ModelMapper modelMapper;

    @Transactional
    public BoardDetailDto getBoard(Long board_id) {
        Board board = this.boardRepository.findById(board_id).orElseThrow(BoardNotFoundException::new);
        BoardDetailDto boardDetailDto = this.modelMapper.map(board, BoardDetailDto.class);
        return boardDetailDto;
    }

    @Transactional
    public void storeBoard(BoardDetailDto boardDetailDto) {
        Board board = new Board();
        boardDetailDto.toEntity(board);
        board.setMember(this.memberRepository.findMemberByEmail(boardDetailDto.getEmail()).orElseThrow(MemberNotFoundException::new));
        this.boardRepository.save(board);
    }

    @Transactional
    public BoardDetailDto updateBoard(Long board_id, BoardDetailDto boardDetailDto) {
        Board board = this.boardRepository.findById(board_id).orElseThrow(BoardNotFoundException::new);
        boardDetailDto.toEntity(board);
        this.boardRepository.save(board);
        BoardDetailDto returnValue = this.modelMapper.map(board, BoardDetailDto.class);
        return returnValue;
    }

    @Transactional
    public void deleteBoard(Long board_id) {
        this.boardRepository.findById(board_id).orElseThrow(BoardNotFoundException::new);
        this.boardRepository.deleteById(board_id);
    }
}
