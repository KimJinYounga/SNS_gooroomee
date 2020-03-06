package com.gooroomee.api.board.boardList;

import com.gooroomee.api.board.Board;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class BoardListResource extends EntityModel<Board> {
    public BoardListResource(Board board, Link... links) {
        super(board, links);
        add(new Link("/board/"+board.getBoardId()).withSelfRel());
    }
}
