package com.gooroomee.api.board.detail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/board")
public class BoardDetailController {
    private final BoardDetailService boardDetailService;

    @GetMapping(value = "/{board_id}")
    public ResponseEntity getBoardDetail(@PathVariable Long board_id){
        BoardDetailDto board = boardDetailService.getBoard(board_id);
        BoardDetailResource boardResource = new BoardDetailResource(board, board_id);
        boardResource.add(linkTo(BoardDetailController.class).slash(board_id).withRel("updateBoard"));
        boardResource.add(linkTo(BoardDetailController.class).slash(board_id).withRel("deleteBoard"));
        return ResponseEntity.ok(boardResource);
    }

    @PostMapping
    public ResponseEntity createBoard(@RequestBody @Valid BoardDetailDto boardDetailDto, Errors errors) {
        try{
            this.boardDetailService.storeBoard(boardDetailDto);
            return ResponseEntity.ok().build();
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body("게시판등록실패");
        }
    }

    @PutMapping("/{board_id}")
    public ResponseEntity updateBoard(@PathVariable Long board_id,
                                      @RequestBody @Valid BoardDetailDto boardDetailDto) {
        try{
            BoardDetailDto updatedBoard = this.boardDetailService.updateBoard(board_id, boardDetailDto);
            BoardDetailResource boardDetailResource = new BoardDetailResource(updatedBoard, board_id);
            return ResponseEntity.ok(boardDetailResource);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{board_id}")
    public ResponseEntity deleteBoard(@PathVariable Long board_id) {
        try{
            this.boardDetailService.deleteBoard(board_id);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }

    }
}
