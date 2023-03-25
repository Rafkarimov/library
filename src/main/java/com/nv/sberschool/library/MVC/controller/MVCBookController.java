package com.nv.sberschool.library.MVC.controller;

import com.nv.sberschool.library.dto.BookDto;
import com.nv.sberschool.library.dto.BookSearchDTO;
import com.nv.sberschool.library.dto.BookWithAuthorsDto;
import com.nv.sberschool.library.mapper.BookMapper;
import com.nv.sberschool.library.mapper.BookWithAuthorsMapper;
import com.nv.sberschool.library.model.Book;
import com.nv.sberschool.library.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String addBook(@ModelAttribute("bookForm") BookDto bookDto) {
        service.create(mapper.toEntity(bookDto));
        return "redirect:/books";
    }

    @GetMapping("/{bookId}")
    public String viewOneBook(@PathVariable Long bookId, Model model) {
        model.addAttribute("book", mapper.toDto(service.getOne(bookId)));
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
    public String searchBooks(@RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "size", defaultValue = "5") int size,
                              @ModelAttribute("bookSearchForm") BookSearchDTO bookSearchDTO,
                              Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, "title"));
        model.addAttribute("books", service.findBooks(bookSearchDTO, pageRequest));
        return "books/viewAllBooks";
    }

}




