package com.nv.sberschool.library.controller;

import com.nv.sberschool.library.model.Author;
import com.nv.sberschool.library.repository.AuthorRepository;
import com.nv.sberschool.library.repository.GenericRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
public class AuthorController extends GenericController<Author> {
    public AuthorController(AuthorRepository authorRepository) {
        super(authorRepository);
    }
}

