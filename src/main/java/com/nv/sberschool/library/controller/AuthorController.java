package com.nv.sberschool.library.controller;

import com.nv.sberschool.library.dto.AuthorDto;
import com.nv.sberschool.library.dto.AuthorWithBooksDto;
import com.nv.sberschool.library.mapper.AuthorMapper;
import com.nv.sberschool.library.mapper.AuthorWithBooksMapper;
import com.nv.sberschool.library.model.Author;
import com.nv.sberschool.library.model.Book;
import com.nv.sberschool.library.repository.BookRepository;
import com.nv.sberschool.library.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

@RestController
@RequestMapping("/authors")
@Tag(name = "Авторы", description = "Контроллер для работы с авторами книг библиотеки")
public class AuthorController extends GenericController<Author, AuthorDto> {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final AuthorMapper mapper;
    private final AuthorWithBooksMapper authorWithBooksMapper;

    public AuthorController(BookRepository bookRepository, AuthorService authorService, AuthorMapper mapper, AuthorWithBooksMapper authorWithBooksMapper) {
        super(authorService, mapper);
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.mapper = mapper;
        this.authorWithBooksMapper = authorWithBooksMapper;
    }

    @Operation(description = "Добавить книгу к автору", method = "addBook")
    @RequestMapping(value = "/addBook", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> addBook(@RequestParam(value = "bookId") Long bookId,
                                             @RequestParam(value = "authorId") Long authorId) {
        try {
            Book book = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("Книга с переданным ID не найдена"));
            Author author = authorService.getOne(authorId);
            author.getBooks().add(book);
            return ResponseEntity.status(HttpStatus.OK).body(mapper.toDto(authorService.update(author)));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/authors-with-books", method = RequestMethod.GET)
    public ResponseEntity<List<AuthorWithBooksDto>> getAuthorsWithBooks() {
        return ResponseEntity.ok().body(authorWithBooksMapper.toDtos(authorService.listAll()));
    }
}
