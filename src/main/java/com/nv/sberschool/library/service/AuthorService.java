package com.nv.sberschool.library.service;

import com.nv.sberschool.library.dto.AddBookDto;
import com.nv.sberschool.library.model.Author;
import com.nv.sberschool.library.repository.AuthorRepository;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AuthorService extends GenericService<Author> {
    private final AuthorRepository repository;
    private final BookService bookService;

    protected AuthorService(AuthorRepository repository, BookService bookService) {
        super(repository);
        this.repository = repository;
        this.bookService = bookService;
    }

    @Override
    public void softDelete(Long id) {
        Author author = getOne(id);
        boolean authorCanBeDeleted = repository.checkAuthorForDeletion(id);
        if(authorCanBeDeleted) {
            author.setDeletedBy("ADMIN"); //TODO переделать с секурити
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
