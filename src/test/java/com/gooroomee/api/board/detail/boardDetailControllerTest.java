package com.gooroomee.api.board.detail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooroomee.api.board.Board;
import com.gooroomee.api.board.BoardRepository;
import com.gooroomee.api.common.TestDescription;
import com.gooroomee.api.member.Member;
import com.gooroomee.api.member.MemberRepository;
import com.gooroomee.api.member.security.SignInDto;
import com.gooroomee.api.member.security.SignUpDto;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
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
    MemberRepository memberRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ModelMapper modelMapper;

    @Test
    @TestDescription("글 생성 성공")
    @WithMockUser(username = "jinyoung.kim@gooroomee.com")
    public void Test() throws Exception {
        SignUpDto signUpDto=this.generateMember("jinyoung.kim@gooroomee.com");
        SignInDto signInDto=SignInDto.builder()
                .email("jinyoung.kim@gooroomee.com")
                .password("1234")
                .build();
        mockMvc.perform(RestDocumentationRequestBuilders.post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(signUpDto)));
        ResultActions results = this.mockMvc.perform(RestDocumentationRequestBuilders.post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsBytes(signInDto)));
        String token = results.andReturn().getResponse().getHeader("token");

        SignUpDto signUpDto1=this.generateMember("rlawlsdud419@gooroomee.com");
        SignInDto signInDto1=SignInDto.builder()
                .email("rlawlsdud419@gooroomee.com")
                .password("1234")
                .build();
        mockMvc.perform(RestDocumentationRequestBuilders.post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(signUpDto1)));
        ResultActions results1 = this.mockMvc.perform(RestDocumentationRequestBuilders.post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsBytes(signInDto1)));
        String token1 = results1.andReturn().getResponse().getHeader("token");


        BoardDetailDto boardDetailDto = BoardDetailDto.builder()
                .title("게시판 제목 입니다! ")
                .email(signInDto.getEmail())
                .content(signInDto.getEmail()+"의 게시판 내용")
                .reg_date(LocalDateTime.now())
                .build();
        BoardDetailDto boardDetailDto1 = BoardDetailDto.builder()
                .title("게시판 제목 입니다! ")
                .email(signInDto1.getEmail())
                .content(signInDto1.getEmail()+"의 게시판 내용")
                .reg_date(LocalDateTime.now())
                .build();

        mockMvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(boardDetailDto))
                .header("X-AUTH-TOKEN", token))
                .andExpect(status().isOk())
                .andDo(print());
        mockMvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(boardDetailDto1))
                .header("X-AUTH-TOKEN", token1))
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
                .param("size", "10")
        )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @TestDescription("게시판 글 수정")
    @WithMockUser(username = "jinyoung.kim@gooroomee.com")
    public void TestC() throws Exception {
        // Given
        Optional<Board> optionalBoard = this.boardRepository.findById(Long.valueOf(1));
        Board board = optionalBoard.get();
        BoardDetailDto boardDetailDto = BoardDetailDto.builder()
                .title("수정한 제목임")
                .reg_date(board.getReg_date())
                .content(board.getContent())
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
    @WithMockUser(username = "jinyoung.kim@gooroomee.com")
    public void deleteBoardSuccess() throws Exception {
        this.mockMvc.perform(delete("/board/1"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @TestDescription("게시글 삭제 실패")
    @WithMockUser(username = "jinyoung.kim@gooroomee.com")
    public void deleteBoardFailed() throws Exception {
        this.mockMvc.perform(delete("/board/40"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    private SignUpDto generateMember(String email){
        SignUpDto signUpDto = SignUpDto.builder()
                .name("name "+email)
                .password("1234")
                .email(email)
                .build();
        return signUpDto;

    }
}
