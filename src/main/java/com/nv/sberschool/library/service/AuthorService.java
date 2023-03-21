package com.nv.sberschool.library.service;

import com.nv.sberschool.library.model.Author;
import com.nv.sberschool.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorService extends GenericService<Author> {
    private final AuthorRepository repository;

    protected AuthorService(AuthorRepository repository) {
        super(repository);
        this.repository = repository;
    }
}

