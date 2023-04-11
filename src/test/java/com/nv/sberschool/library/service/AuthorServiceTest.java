package com.nv.sberschool.library.service;

import static org.junit.jupiter.api.Assertions.*;

import com.nv.sberschool.library.AuthorTestData;
import com.nv.sberschool.library.dto.AddBookDto;
import com.nv.sberschool.library.model.Author;
import com.nv.sberschool.library.repository.AuthorRepository;
import com.nv.sberschool.library.service.userdetails.CustomUserDetails;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class AuthorServiceTest extends GenericServiceTest<Author> {

    public AuthorServiceTest() {
        super();
        BookService bookService = Mockito.mock(BookService.class);
        repository = Mockito.mock(AuthorRepository.class);
        service = new AuthorService((AuthorRepository) repository, bookService);
    }

    @Test
    @Order(1)
    void listAll() {
        Mockito.when(repository.findAll()).thenReturn(AuthorTestData.AUTHOR_LIST);
        List<Author> authorList = service.listAll();
        authorList.forEach(i -> {
            System.out.println(i.getAuthorFio());
        });
        assertEquals(AuthorTestData.AUTHOR_LIST, authorList);
    }

    @Test
    @Order(2)
    void getOne() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(AuthorTestData.AUTHOR_1));
        Author author = service.getOne(1L);
        assertEquals(AuthorTestData.AUTHOR_1, author);
    }

//  Тест для сервиса с мапперами
//  protected void create() {
//    Mockito.when(mapper.toEntity(AuthorTestData.AUTHOR_DTO_1)).thenReturn(AuthorTestData.AUTHOR_1);
//    Mockito.when(mapper.toDTO(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_DTO_1);
//    Mockito.when(repository.save(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_1);
//    AuthorDTO authorDTO = service.create(AuthorTestData.AUTHOR_DTO_1);
//    log.info("Testing create(): " + authorDTO);
//    assertEquals(AuthorTestData.AUTHOR_DTO_1, authorDTO);
//  }

    @Test
    @Order(3)
    void create() {
        Mockito.when(repository.save(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_1);
        Author author = service.create(AuthorTestData.AUTHOR_1);
        log.info("Created author: " + author.getAuthorFio());
        assertEquals(AuthorTestData.AUTHOR_1, author);
    }

    @Test
    void update() {
        Mockito.when(repository.save(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_1);
        Author author = service.update(AuthorTestData.AUTHOR_1);
        log.info("Updated author: " + author.getAuthorFio());
        assertEquals(AuthorTestData.AUTHOR_1, author);
    }

    @Test
    void delete() {
        service.delete(1L);
    }

    @Test
    void softDelete() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(AuthorTestData.AUTHOR_1));
        Mockito.when(((AuthorRepository) repository).checkAuthorForDeletion(1L)).thenReturn(true);
        Mockito.when(repository.save(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_1);
        service.softDelete(1L);
        log.info("Test delete: " + AuthorTestData.AUTHOR_1.isDeleted());
        assertTrue(AuthorTestData.AUTHOR_1.isDeleted());
    }

    @Test
    void restore() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(AuthorTestData.AUTHOR_1));
        Mockito.when(repository.save(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_1);
        service.restore(1L);
        log.info("Test delete: " + AuthorTestData.AUTHOR_1.isDeleted());
        assertFalse(AuthorTestData.AUTHOR_1.isDeleted());
    }

    @Test
    void findByCreatedBy() {
        Mockito.when(repository.findByCreatedBy("createdBy")).thenReturn(AuthorTestData.AUTHOR_LIST);
        List<Author> authorList = service.findByCreatedBy("createdBy");
        assertEquals(AuthorTestData.AUTHOR_LIST, authorList);
    }

    @Test
    void existsById() {
        Mockito.when(repository.existsById(1L)).thenReturn(true);
        boolean exist = service.existsById(1L);
        assertTrue(exist);
    }

    @Test
    void searchAuthors() {
        PageRequest pageRequest = PageRequest.of(1, 10, Sort.by(Direction.ASC, "authorFio"));
        Mockito.when(((AuthorRepository) repository).findAllByAuthorFioContainsIgnoreCaseAndIsDeletedFalse("authorFio", pageRequest))
                .thenReturn(new PageImpl<>(AuthorTestData.AUTHOR_LIST));

        Page<Author> authorPage = ((AuthorService) service).searchAuthors("authorFio", pageRequest);
        assertEquals(AuthorTestData.AUTHOR_LIST, authorPage.getContent());
    }

    @Test
    void addBook() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(AuthorTestData.AUTHOR_1));
        Mockito.when(repository.save(AuthorTestData.AUTHOR_1)).thenReturn(AuthorTestData.AUTHOR_1);
        ((AuthorService) service).addBook(new AddBookDto(1L, 1L));
        log.info(String.valueOf(AuthorTestData.AUTHOR_1.getBooks().size()));
        assertTrue(AuthorTestData.AUTHOR_1.getBooks().size() >= 1);
    }
}
