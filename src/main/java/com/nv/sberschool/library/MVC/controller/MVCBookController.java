package com.nv.sberschool.library.MVC.controller;

import com.nv.sberschool.library.dto.BookDto;
import com.nv.sberschool.library.mapper.BookMapper;
import com.nv.sberschool.library.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class MVCBookController {

    private final BookService service;
    private final BookMapper mapper;

    public MVCBookController(BookService service, BookMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("")
    public String getAll(Model model) {
        model.addAttribute("books", mapper.toDtos(service.listAll()));
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
        service.delete(bookId);
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

}

