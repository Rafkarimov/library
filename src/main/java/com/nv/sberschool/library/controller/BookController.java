package com.nv.sberschool.library.controller;

import com.nv.sberschool.library.dto.BookDto;
import com.nv.sberschool.library.dto.BookWithAuthorsDto;
import com.nv.sberschool.library.mapper.BookMapper;
import com.nv.sberschool.library.mapper.BookWithAuthorsMapper;
import com.nv.sberschool.library.model.Author;
import com.nv.sberschool.library.model.Book;
import com.nv.sberschool.library.service.AuthorService;
import com.nv.sberschool.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import java.util.List;

@RestController
@RequestMapping("/rest/books")
@Tag(name = "Книги", description = "Контроллер для работы с книгами библиотеки")
public class BookController extends GenericController<Book, BookDto> {

    private final BookService service;
    private final AuthorService authorService;
    private final BookMapper mapper;
    private final BookWithAuthorsMapper bookWithAuthorsMapper;

    public BookController(BookService service, AuthorService authorService,
                          BookMapper mapper, BookWithAuthorsMapper bookWithAuthorsMapper) {
        super(service, mapper);
        this.service = service;
        this.authorService = authorService;
        this.mapper = mapper;
        this.bookWithAuthorsMapper = bookWithAuthorsMapper;
    }

    @Operation(description = "Добавить автора к книге", method = "addAuthor")
    @RequestMapping(value = "/addAuthor", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> addAuthor(@RequestParam(value = "authorId") Long authorId,
                                             @RequestParam(value = "bookId") Long bookId) {
        try {
            Book book = service.getOne(bookId);
            Author author = authorService.getOne(authorId);
            book.getAuthors().add(author);

            return ResponseEntity.status(HttpStatus.OK).body(mapper.toDto(service.update(book)));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(description = "Просмотреть список книг с авторами", method = "addAuthor")
    @RequestMapping(value = "/get-books-with-authors", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookWithAuthorsDto>> getBooksWithAuthors() {
        return ResponseEntity.ok().body(bookWithAuthorsMapper.toDtos(service.listAll()));
    }
}
