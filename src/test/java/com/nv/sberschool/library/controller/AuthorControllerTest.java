package com.nv.sberschool.library.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nv.sberschool.library.config.jwt.JWTTokenUtil;
import com.nv.sberschool.library.dto.AuthorDto;
import com.nv.sberschool.library.service.userdetails.CustomUserDetailsService;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@Slf4j
public class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    private String token = "";

    protected ObjectMapper objectMapper = new ObjectMapper();

    private HttpHeaders headers = new HttpHeaders();
    private String generateToken(String username) {
        return jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(username));
    }

    @BeforeAll
    public void prepare() {
        token = generateToken("librarian");
        headers.add("Authorization", "Bearer " + token);
    }

    @Test
    void getAll() throws Exception {
        String result = mvc.perform(get("/rest/authors/getAll")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        log.info(result);
        //TODO не работает
//    List<AuthorDto> authorDtoList = objectMapper.readValue(result, new TypeReference<List<AuthorDto>>() {});
//    authorDtoList.forEach(authorDto -> log.info(authorDto.getAuthorFio()));
    }

    @Test
    void create() throws Exception {
        AuthorDto authorDTO = new AuthorDto("REST_TestAuthorFio", "2023-01-01", "Test Description", new HashSet<>());
        String result = mvc.perform(post("/rest/authors/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(asJsonString(authorDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        AuthorDto created = objectMapper.readValue(result, AuthorDto.class);
        log.info(String.valueOf(created.getId()));
    }

    @Test
    void update() throws Exception {

        AuthorDto existingAuthor = objectMapper.readValue(mvc.perform(get("/rest/authors/getById")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .headers(headers)
                                .param("id", String.valueOf(4L))
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().is2xxSuccessful())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                AuthorDto.class);

        existingAuthor.setAuthorFio("UPDATED");
        mvc.perform(put("/rest/authors/update")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .param("id", String.valueOf(existingAuthor.getId()))
                        .content(asJsonString(existingAuthor))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void addBook() {
    }

    @Test
    void getAuthorsWithBooks() {
    }

    protected String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        }
        catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
