package com.nv.sberschool.library.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.nv.sberschool.library.dto.AddBookDto;
import com.nv.sberschool.library.dto.AuthorDto;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@Slf4j
public class AuthorControllerTest extends GenericControllerTest {

    private static final String BASE_URL = "/rest/authors";

    @Override
    @Test
    void getById() {

    }

    @Override
    @Test
    void getByCreator() {

    }

    @Test
    void getAll() throws Exception {
        String result = super.mvc.perform(get(BASE_URL + "/getAll")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        log.info(result);
        List<AuthorDto> authorDtoList = objectMapper.readValue(result, new TypeReference<List<AuthorDto>>() {
        });
        authorDtoList.forEach(authorDto -> log.info(authorDto.getAuthorFio()));
    }

    @Test
    void create() throws Exception {
        AuthorDto authorDTO = new AuthorDto("REST_TestAuthorFio", LocalDate.now(), "Test Description", new HashSet<>());
        String result = mvc.perform(post(BASE_URL + "/add")
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

        AuthorDto existingAuthor = objectMapper.readValue(mvc.perform(get(BASE_URL + "/getById")
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

    @Override
    @Test
    void deleteObject() {
    }

    @Test
    protected void softDelete() throws Exception {
        mvc.perform(delete(BASE_URL + "/soft-delete/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        AuthorDto existingAuthor = objectMapper.readValue(
                mvc.perform(get(BASE_URL + "/getById")
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .headers(headers)
                                .param("id", String.valueOf(1L))
                        )
                        .andExpect(status().is2xxSuccessful())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(), AuthorDto.class);
        assertTrue(existingAuthor.isDeleted());
    }

    @Test
    protected void restore() throws Exception {
        mvc.perform(put(BASE_URL + "/restore/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        AuthorDto existingAuthor = objectMapper.readValue(
                mvc.perform(get(BASE_URL + "/getById")
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .headers(headers)
                                .param("id", String.valueOf(1L))
                        )
                        .andExpect(status().is2xxSuccessful())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(), AuthorDto.class);
        assertFalse(existingAuthor.isDeleted());
    }

    @Test
    void addBook() throws Exception {
        AddBookDto addBookDto = new AddBookDto(1L, 2L);
        String result = mvc.perform(post(BASE_URL + "/addBook")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .headers(headers)
                        .content(asJsonString(addBookDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        AuthorDto authorDto = objectMapper.readValue(result, AuthorDto.class);
        assertTrue(authorDto.getBooksId().contains(1L));
    }

    protected String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
