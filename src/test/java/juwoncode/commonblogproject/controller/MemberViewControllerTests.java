package juwoncode.commonblogproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import juwoncode.commonblogproject.dto.MemberDto;
import juwoncode.commonblogproject.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberViewController.class)
public class MemberViewControllerTests {
    @MockBean
    MemberService memberService;
    @Autowired
    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("회원가입 서비스 호출 테스트 (성공)")
    @Test
    void test_callRegisterService_when_success() throws Exception {
        MemberDto.RequestDto dto =
                new MemberDto.RequestDto("username", "password", "username@email.com");
        given(memberService.register(any(MemberDto.RequestDto.class))).willReturn(true);

        mockMvc.perform(post("/member/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/member/validate/email"))
                .andExpect(flash().attribute("message", "회원가입이 성공했습니다!"));
    }

    @DisplayName("회원가입 서비스 호출 테스트 (실패)")
    @Test
    void test_callRegisterService_when_failure() throws Exception {
        MemberDto.RequestDto dto =
                new MemberDto.RequestDto("username", "password", "username@email.com");
        given(memberService.register(any(MemberDto.RequestDto.class))).willReturn(false);

        mockMvc.perform(post("/member/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/member/register"))
                .andExpect(flash().attribute("message", "회원가입이 실패했습니다!"));
    }
}
