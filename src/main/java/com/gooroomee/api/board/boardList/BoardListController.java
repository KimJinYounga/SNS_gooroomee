package com.gooroomee.api.board.boardList;

import com.gooroomee.api.board.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
@CrossOrigin(origins = "*")
public class BoardListController {
    private final BoardListService boardListService;

    /*
    게시판 전체 리스트 검색
     */
    @GetMapping
    public ResponseEntity getBoardsList(Pageable pageable, PagedResourcesAssembler<Board> assembler) {
        Page<Board> page = this.boardListService.getBoardsList(pageable);
        if (page==null)
            return ResponseEntity.notFound().build();

        PagedModel<BoardListResource> pagedResources = assembler.toModel(page, e -> new BoardListResource(e));
        pagedResources.add(linkTo(BoardListController.class).withRel("boards-list"));

        return ResponseEntity.ok(pagedResources);
    }
}
