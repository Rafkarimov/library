package com.nv.sberschool.library.service;

import com.nv.sberschool.library.model.Book;
import com.nv.sberschool.library.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService extends GenericService<Book> {

    private final BookRepository repository;

    protected BookService(BookRepository repository) {
        super(repository);
        this.repository = repository;
    }

    //TODO подумать
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
        return super.update(book);
    }
}

