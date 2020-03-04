package com.gooroomee.api.boardList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooroomee.api.common.TestDescription;
import com.gooroomee.api.user.SignInDto;
import com.gooroomee.api.user.User;
import com.gooroomee.api.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ModelMap;

import java.util.Date;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BoardListControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    BoardListRepository boardListRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ModelMapper modelMapper;

    @Test
    @TestDescription("게시판 전체 글 불러오기")
    public void getboardsList() throws Exception {
        // Given
        IntStream.range(0,30).forEach(i -> {
            this.generateBoards(i);
        });

        // When & Then
        this.mockMvc.perform(get("/board")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "title,DESC")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists());


    }

    private void generateBoards(int index) {
        SignInDto signInDto = SignInDto.builder()
                .email("jinyoung.kim@gooroomee.com"+index)
                .build();
        User user = this.modelMapper.map(signInDto, User.class);
        userRepository.save(user);

        BoardList boardList = BoardList.builder()
                .title("글 제목 "+index)
                .date(new Date())
                .contents("글 상세 정보 입니다 "+index*10)
                .user(user)
                .build();
        this.boardListRepository.save(boardList);
    }



}