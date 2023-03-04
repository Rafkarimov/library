package com.nv.sberschool.library.repository;

import com.nv.sberschool.library.model.Book;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Hidden
public interface BookRepository extends GenericRepository<Book> {
    List<Book> findByBookTitle(String bookTitle);
    List<Book> findByBookTitleAndStoragePlace(String bookTitle, String storagePlace);
    List<Book> findByBookTitleOrStoragePlace(String bookTitle, String storagePlace);
}


