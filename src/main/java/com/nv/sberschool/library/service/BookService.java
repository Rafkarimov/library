package com.nv.sberschool.library.service;

import com.nv.sberschool.library.dto.BookSearchDTO;
import com.nv.sberschool.library.dto.BookWithAuthorsDto;
import com.nv.sberschool.library.mapper.BookWithAuthorsMapper;
import com.nv.sberschool.library.model.Book;
import com.nv.sberschool.library.repository.BookRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BookService extends GenericService<Book> {

    private final BookRepository repository;
    private final BookWithAuthorsMapper bookWithAuthorsMapper;

    protected BookService(BookRepository repository, BookWithAuthorsMapper bookWithAuthorsMapper) {
        super(repository);
        this.repository = repository;
        this.bookWithAuthorsMapper = bookWithAuthorsMapper;
    }

    @Override
    public Book update(Book object) {
        Book book = getOne(object.getId());
        book.setBookTitle(object.getBookTitle() != null ? object.getBookTitle() : book.getBookTitle());
        book.setPublishDate(object.getPublishDate() != null ? object.getPublishDate() : book.getPublishDate());
        book.setPageCount(object.getPageCount() != null ? object.getPageCount() : book.getPageCount());
        book.setAmount(object.getAmount() != null ? object.getAmount() : book.getAmount());
        book.setStoragePlace(object.getStoragePlace() != null ? object.getStoragePlace() : book.getStoragePlace());
        book.setOnlineCopyPath(object.getOnlineCopyPath() != null ? object.getOnlineCopyPath() : book.getOnlineCopyPath());
        book.setGenre(object.getGenre() != null ? object.getGenre() : book.getGenre());
        book.setAuthors(object.getAuthors() != null ? object.getAuthors() : book.getAuthors());
        book.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        book.setUpdatedWhen(LocalDateTime.now());
        return super.update(book);
    }

    public Page<BookWithAuthorsDto> findBooks(BookSearchDTO bookSearchDTO,
                                              Pageable pageable) {
        String genre = bookSearchDTO.getGenre() != null ? String.valueOf(bookSearchDTO.getGenre().ordinal()) : "%";
        Page<Book> bookPage = repository.searchBooks(
                genre,
                bookSearchDTO.getBookTitle(),
                bookSearchDTO.getAuthorFio(),
                pageable
        );
        List<BookWithAuthorsDto> result = bookWithAuthorsMapper.toDtos(bookPage.getContent());
        return new PageImpl<>(result, pageable, bookPage.getTotalElements());
    }
}

