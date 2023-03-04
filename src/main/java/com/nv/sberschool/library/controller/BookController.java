package com.nv.sberschool.library.controller;

import com.nv.sberschool.library.model.Book;
import com.nv.sberschool.library.repository.BookRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController extends GenericController<Book> {

    public BookController(BookRepository bookRepository) {
        super(bookRepository);
    }
}

