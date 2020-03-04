package com.gooroomee.api.board.boardDetail;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class BoardDetailResource extends EntityModel<BoardDetailDto> {
    public BoardDetailResource(BoardDetailDto board, long boardId, Link... links) {
        super(board, links);
        add(new Link("/board/"+boardId).withSelfRel());
    }
}
