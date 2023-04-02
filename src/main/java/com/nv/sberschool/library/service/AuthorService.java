package com.nv.sberschool.library.service;

import com.nv.sberschool.library.dto.AddBookDto;
import com.nv.sberschool.library.model.Author;
import com.nv.sberschool.library.repository.AuthorRepository;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthorService extends GenericService<Author> {
    private final AuthorRepository repository;
    private final BookService bookService;

    protected AuthorService(AuthorRepository repository, BookService bookService) {
        super(repository);
        this.repository = repository;
        this.bookService = bookService;
    }

    @Override
    @Secured(value = "ROLE_LIBRARIAN")
    public void softDelete(Long id) {
        log.error("SOFT");
        Author author = getOne(id);
        boolean authorCanBeDeleted = repository.checkAuthorForDeletion(id);
        if(authorCanBeDeleted) {
            author.setDeletedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            author.setDeleted(true);
            author.setDeletedWhen(LocalDateTime.now());
            update(author);
        }
    }

    public Page<Author> searchAuthors(final String fio, Pageable pageable) {
        return repository.findAllByAuthorFioContainsIgnoreCaseAndIsDeletedFalse(fio, pageable);
    }

    public void addBook(AddBookDto addBookDto) {
        Author author = getOne(addBookDto.getAuthorId());
        author.getBooks().add(bookService.getOne(addBookDto.getBookId()));
        update(author);
    }
}
