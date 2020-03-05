package com.gooroomee.api.board.boardDetail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooroomee.api.board.Board;
import com.gooroomee.api.board.BoardRepository;
import com.gooroomee.api.common.TestDescription;
import com.gooroomee.api.user.User;
import com.gooroomee.api.user.UserRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class boardDetailControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ModelMapper modelMapper;

    @Test
    @TestDescription("글 생성 성공")
    public void Test() throws Exception {
        this.generateUser("User01");
        BoardDetailDto boardDetailDto = BoardDetailDto.builder()
                .title("게시판 제목 입니다! ")
                .email("User01")
                .contents("User01의 게시판 내용")
                .date(new Date())
                .build();
        BoardDetailDto boardDetailDto1 = BoardDetailDto.builder()
                .title("게시판 제목 입니다! ")
                .email("User02")
                .contents("User02의 게시판 내용")
                .date(new Date())
                .build();

        mockMvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(boardDetailDto)))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(boardDetailDto1)))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @TestDescription("게시판 글 상세정보 불러오기")
    public void TestB() throws Exception{
        // Given

        // When & Then
        this.mockMvc.perform(get("/board/1")
                .param("page", "0")
                .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @TestDescription("게시판 글 수정")
    public void TestC() throws Exception {
        // Given
        Optional<Board> optionalBoard = this.boardRepository.findById((long) 1);
        Board board = optionalBoard.get();
        BoardDetailDto boardDetailDto = BoardDetailDto.builder()
                .title("수정한 제목임")
                .date(board.getDate())
                .contents(board.getContents())
                .email(board.getEmail())
                .build();
        // When & Then
        this.mockMvc.perform(put("/board/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(boardDetailDto)))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @TestDescription("게시글 삭제 성공")
    public void deleteBoardSuccess() throws Exception {
        this.mockMvc.perform(delete("/board/2"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @TestDescription("게시글 삭제 실패")
    public void deleteBoardFailed() throws Exception {
        this.mockMvc.perform(delete("/board/40"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    private void generateUser(String user01) {
        User user = User.builder()
                .email(user01)
                .password("123")
                .name("김진영")
                .build();
        this.userRepository.save(user);
    }
}
