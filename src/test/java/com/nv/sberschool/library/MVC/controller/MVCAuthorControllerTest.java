package com.nv.sberschool.library.MVC.controller;


import com.nv.sberschool.library.dto.AuthorDto;
import com.nv.sberschool.library.mapper.AuthorMapper;
import com.nv.sberschool.library.service.AuthorService;
import java.time.LocalDate;
import java.util.HashSet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlTemplate;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Slf4j
@Transactional
@Rollback(value = false)
public class MVCAuthorControllerTest extends CommonTestMVC {

    //Создаем нового автора для создания через контроллер (тест дата)
    private final AuthorDto authorDTO = new AuthorDto("MVC_TestAuthorFio", LocalDate.now(), "Test Description", new HashSet<>());
    private final AuthorDto authorDTOUpdated = new AuthorDto("MVC_TestAuthorFio_UPDATED", LocalDate.now(), "Test Description", new HashSet<>());

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorMapper mapper;

    @Test
    @DisplayName("Получение всех авторов")
    protected void listAll() throws Exception {
        mvc.perform(get("/authors")
                        .param("page", "1")
                        .param("size", "5")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("authors/viewAllAuthors"))
                .andExpect(model().attributeExists("authors"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void createObject() throws Exception {
        mvc.perform(post("/authors/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("authorForm", authorDTO)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/authors"))
                .andExpect(redirectedUrlTemplate("/authors"));
    }

    //TODO 403 forbidden
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void updateObject() throws Exception {
        log.info("Тест по обновлению автора через MVC начат успешно");
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "authorFio"));
        AuthorDto foundAuthorForUpdate = mapper.toDto(authorService.searchAuthors("REST_TestAuthorFio", pageRequest).getContent().get(0));
        foundAuthorForUpdate.setAuthorFio(authorDTOUpdated.getAuthorFio());
        mvc.perform(post("/authors/update")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("authorForm", foundAuthorForUpdate)
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/authors"))
                .andExpect(redirectedUrl("/authors"));
        log.info("Тест по обновлению автора через MVC закончен успешно");
    }

    @Override
    protected void deleteObject() throws Exception {

    }
}
