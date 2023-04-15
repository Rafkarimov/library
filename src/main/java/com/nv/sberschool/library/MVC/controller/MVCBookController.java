package com.nv.sberschool.library.MVC.controller;

import com.nv.sberschool.library.dto.AuthorDto;
import com.nv.sberschool.library.dto.BookDto;
import com.nv.sberschool.library.dto.BookSearchDTO;
import com.nv.sberschool.library.dto.BookWithAuthorsDto;
import com.nv.sberschool.library.mapper.BookMapper;
import com.nv.sberschool.library.mapper.BookWithAuthorsMapper;
import com.nv.sberschool.library.model.Book;
import com.nv.sberschool.library.service.BookService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/books")
public class MVCBookController {

    private final BookService service;
    private final BookMapper mapper;
    private final BookWithAuthorsMapper bookWithAuthorsMapper;

    public MVCBookController(BookService service, BookMapper mapper, BookWithAuthorsMapper bookWithAuthorsMapper) {
        this.service = service;
        this.mapper = mapper;
        this.bookWithAuthorsMapper = bookWithAuthorsMapper;
    }
//
//  @GetMapping("")
//  public String getAll(Model model) {
//    model.addAttribute("books", mapper.toDtos(service.listAll()));
//    return "books/viewAllBooks";
//  }

    @GetMapping("")
    public String getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int pageSize,
            Model model
    ) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Direction.ASC, "bookTitle"));
        Page<Book> bookPage = service.listAll(pageRequest);
        Page<BookWithAuthorsDto> authorDtoPage = new PageImpl<>(bookWithAuthorsMapper.toDtos(bookPage.getContent()), pageRequest, bookPage.getTotalElements());
        model.addAttribute("books", authorDtoPage);
        return "books/viewAllBooks";
    }

    @GetMapping("/add")
    public String addBook() {
        return "books/addBook";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute("bookForm") BookDto bookDto, @RequestParam MultipartFile file) throws IOException {
        if(file != null && file.getSize() > 0) {
            service.create(mapper.toEntity(bookDto), file);
        } else {
            service.create(mapper.toEntity(bookDto));
        }
        return "redirect:/books";
    }

    @GetMapping("/{bookId}")
    public String viewOneBook(@PathVariable Long bookId, Model model) {
        model.addAttribute("book", bookWithAuthorsMapper.toDto(service.getOne(bookId)));
        return "/books/viewBook";
    }

    @GetMapping("/delete/{bookId}")
    public String delete(@PathVariable Long bookId) {
        service.softDelete(bookId);
        return "redirect:/books";
    }

    @GetMapping("/restore/{id}")
    public String restore(@PathVariable Long id) {
        service.restore(id);
        return "redirect:/books";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        model.addAttribute("book", mapper.toDto(service.getOne(id)));
        return "books/updateBook";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("bookForm") BookDto bookDto) {
        service.update(mapper.toEntity(bookDto));
        return "redirect:/books";
    }

    @PostMapping("/search")
    public String searchBooks(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @ModelAttribute("bookSearchForm") BookSearchDTO bookSearchDTO,
            Model model
    ) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, "title"));
        model.addAttribute("books", service.findBooks(bookSearchDTO, pageRequest));
        return "books/viewAllBooks";
    }

    @PostMapping("/search/author")
    public String searchBooks(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @ModelAttribute("authorSearchForm") AuthorDto authorDto,
            Model model
    ) {
        BookSearchDTO bookSearchDTO = new BookSearchDTO();
        bookSearchDTO.setAuthorFio(authorDto.getAuthorFio());
        return searchBooks(page, size, bookSearchDTO, model);
    }

    @GetMapping(value = "/download", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> downloadBook(@Param(value = "bookId") Long bookId) throws IOException {
        Book book = service.getOne(bookId);
        Path path = Paths.get(book.getOnlineCopyPath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        return ResponseEntity.ok().headers(headers(path.getFileName().toString()))
                .contentLength(path.toFile().length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    private HttpHeaders headers(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return headers;
    }
}

