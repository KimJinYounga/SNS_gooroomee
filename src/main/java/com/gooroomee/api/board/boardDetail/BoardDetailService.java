package com.gooroomee.api.board.boardDetail;

import com.gooroomee.api.board.Board;
import com.gooroomee.api.board.BoardRepository;
import com.gooroomee.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardDetailService {
    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public BoardDetailDto getBoard(Long board_id) {
        Optional<Board> optionalBoard = this.boardRepository.findById(board_id);
        if (optionalBoard.isEmpty())
            return null;
        Board board = optionalBoard.get();
        BoardDetailDto boardDetailDto = this.modelMapper.map(board, BoardDetailDto.class);
        return boardDetailDto;

    }

    public Boolean storeBoard(BoardDetailDto boardDetailDto) {
        Board board = this.modelMapper.map(boardDetailDto, Board.class);
        board.setUser(this.userRepository.findUserByEmail(board.getEmail()));
        this.boardRepository.save(board);
        return true;
    }

    public BoardDetailDto updateBoard(Long board_id, BoardDetailDto boardDetailDto) {
        Optional<Board> optionalBoard = this.boardRepository.findById(board_id);
        if (optionalBoard.isEmpty())
            return null;
        Board board = optionalBoard.get();
        this.modelMapper.map(boardDetailDto, board);
        this.boardRepository.save(board);
        BoardDetailDto returnValue = this.modelMapper.map(board, BoardDetailDto.class);
        return returnValue;
    }

    public boolean deleteBoard(Long board_id) {
        Optional<Board> optionalBoard = this.boardRepository.findById(board_id);
        if(optionalBoard.isEmpty())
            return false;
        this.boardRepository.deleteById(board_id);
        return true;
    }
}
