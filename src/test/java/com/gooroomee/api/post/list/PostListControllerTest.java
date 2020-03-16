package com.gooroomee.api.post.list;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooroomee.api.board.Board;
import com.gooroomee.api.board.BoardRepository;
import com.gooroomee.api.board.BoardType;
import com.gooroomee.api.common.TestDescription;
import com.gooroomee.api.member.MemberRepository;
import com.gooroomee.api.member.security.SignInDto;
import com.gooroomee.api.member.security.SignUpDto;
import com.gooroomee.api.post.PostRepository;
import com.gooroomee.api.post.detail.PostDetailDto;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
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
public class PostListControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @TestDescription("게시판 생성")
    @WithMockUser(username = "jinyoung.kim@gooroomee.com")
    public void Tesa() throws Exception{
        Board free=Board.builder()
                .boardType(BoardType.free.toString())
                .build();
        Board QnA=Board.builder()
                .boardType(BoardType.QnA.toString())
                .build();
        Board FAQ=Board.builder()
                .boardType(BoardType.FAQ.toString())
                .build();
        Board notice=Board.builder()
                .boardType(BoardType.notice.toString())
                .build();
        boardRepository.save(free);
        boardRepository.save(QnA);
        boardRepository.save(FAQ);
        boardRepository.save(notice);
    }

    @Test
    @TestDescription("게시판 전체 글 불러오기")
    public void TestA() throws Exception {
        // Given
        SignUpDto signUpDto=this.generateUser("jinyoung.kim@gooroomee.com");
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

        IntStream.range(0,30).forEach(i -> {
            PostDetailDto postDetailDto = this.generatePosts(i, signUpDto);

            try {
                mockMvc.perform(post("/post/free")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(postDetailDto))
                        .header("X-AUTH-TOKEN", token)
                )
                        .andExpect(status().isOk());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // When & Then
        this.mockMvc.perform(get("/posts/free")
                .param("page", "0")
                .param("size", "10"))

                .andDo(print())
                .andExpect(status().isOk());
    }

    private SignUpDto generateUser(String email){
        SignUpDto signUpDto = SignUpDto.builder()
                .name("name "+email)
                .password("1234")
                .email(email)
                .build();
        return signUpDto;

    }

    private PostDetailDto generatePosts(int index, SignUpDto signUpDto) {
        PostDetailDto postList = PostDetailDto.builder()
                .title("글 제목 "+index)
                .reg_date(LocalDateTime.now())
                .content("글 상세 정보 입니다 "+index*10)
                .email(signUpDto.getEmail())
                .build();

        return postList;
    }

}
