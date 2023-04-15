package com.nv.sberschool.library.controller;

import com.nv.sberschool.library.dto.AddBookDto;
import com.nv.sberschool.library.dto.AuthorDto;
import com.nv.sberschool.library.dto.AuthorWithBooksDto;
import com.nv.sberschool.library.mapper.AuthorMapper;
import com.nv.sberschool.library.mapper.AuthorWithBooksMapper;
import com.nv.sberschool.library.model.Author;
import com.nv.sberschool.library.model.Book;
import com.nv.sberschool.library.repository.BookRepository;
import com.nv.sberschool.library.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

@RestController
@RequestMapping("/rest/authors")
@Tag(name = "Авторы", description = "Контроллер для работы с авторами книг библиотеки")
@SecurityRequirement(name = "Bearer Authentication")
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
    @RequestMapping(value = "/addBook", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> addBook(@RequestBody AddBookDto addBookDto) {
        try {
            Book book = bookRepository.findById(addBookDto.getBookId()).orElseThrow(() -> new NotFoundException("Книга с переданным ID не найдена"));
            Author author = authorService.getOne(addBookDto.getAuthorId());
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
