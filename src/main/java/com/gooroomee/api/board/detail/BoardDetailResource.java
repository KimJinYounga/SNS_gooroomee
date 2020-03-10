package com.gooroomee.api.board.detail;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class BoardDetailResource extends EntityModel<BoardDetailDto> {
    public BoardDetailResource(BoardDetailDto board, Long boardId, Link... links) {
        super(board, links);
        add(new Link("/board/"+boardId).withSelfRel());
    }
}
