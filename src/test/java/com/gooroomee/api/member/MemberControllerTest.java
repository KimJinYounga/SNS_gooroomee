package com.gooroomee.api.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooroomee.api.common.TestDescription;
import com.gooroomee.api.member.security.SignInDto;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MemberControllerTest {
    private static final StaticApplicationContext applicationContext = new StaticApplicationContext();
    private static final WebMvcConfigurationSupport webMvcConfigurationSupport = new WebMvcConfigurationSupport();

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MemberRepository memberRepository;

//    @InjectMocks
//    private MemberController controller;
//
//    @Before
//    public void setup(){
//        applicationContext.registerSingleton("exceptionHandler", ExceptionHandleController.class);
//        webMvcConfigurationSupport.setApplicationContext(applicationContext);
//
//        mockMvc = MockMvcBuilders.standaloneSetup(controller)
//                .setHandlerExceptionResolvers(webMvcConfigurationSupport.handlerExceptionResolver())
//                .alwaysDo(print())
//                .build();
//    }

    @Test
    @TestDescription("회원가입_아이디 중복 없을 때 성공 테스트")
    public void TestA() throws Exception {
        // Given
        Member member = this.memberBuilder();

        // When & Then
        this.mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(member)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @TestDescription("회원가입_아이디 중복 있을 때 실패 테스트(다시하기)")
    public void TestB() throws Exception {
        // Given
        Member existingEmail = Member.builder()
                .email("jinyoung.kim@gooroomee.com")
                .password("1234")
                .name("KJY")
                .build();

        // When & Then
        this.mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(existingEmail)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @TestDescription("로그인")
    public void TestC() throws Exception {
        // Given
        SignInDto signInDto = SignInDto.builder()
                .email("jinyoung.kim@gooroomee.com")
                .password("1234")
                .build();

        this.mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsBytes(signInDto)))
                .andExpect(status().isOk())
                .andDo(print());

    }


    private Member memberBuilder() {
        Member member = Member.builder()
                .email("jinyoung.kim@gooroomee.com")
                .password("1234")
                .name("jinyoung.kim")
                .build();
        return member;
    }


}
