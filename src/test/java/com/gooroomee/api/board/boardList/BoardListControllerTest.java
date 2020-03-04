package com.gooroomee.api.board.boardList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooroomee.api.board.BoardRepository;
import com.gooroomee.api.board.boardDetail.BoardDetailDto;
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
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BoardListControllerTest {
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
    @TestDescription("게시판 전체 글 불러오기")
    public void TestA() throws Exception {
        // Given
        IntStream.range(0,30).forEach(i -> {
            BoardDetailDto boardDetailDto = this.generateBoards(i);

            try {
                mockMvc.perform(post("/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(boardDetailDto)))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // When & Then
        this.mockMvc.perform(get("/board")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "title,DESC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists());
    }

    private BoardDetailDto generateBoards(int index) {
        User user = User.builder()
                .name("name"+index)
                .password("pw"+index)
                .email("jinyoung.kim@gooroomee.com"+index)
                .build();
        userRepository.save(user);

        BoardDetailDto boardList = BoardDetailDto.builder()
                .title("글 제목 "+index)
                .date(new Date())
                .contents("글 상세 정보 입니다 "+index*10)
                .email(user.getEmail())
                .build();

        return boardList;
    }

}
