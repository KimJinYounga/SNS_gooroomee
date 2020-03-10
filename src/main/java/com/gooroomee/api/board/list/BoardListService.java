package com.gooroomee.api.board.list;

import com.gooroomee.api.board.Board;
import com.gooroomee.api.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardListService {
    private final BoardRepository boardRepository;


    public Page<Board> getBoardsList(Pageable pageable) {
        Page<Board> page = this.boardRepository.findAll(pageable);
        return page;
    }
}
