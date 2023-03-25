package com.nv.sberschool.library.repository;

import com.nv.sberschool.library.model.Book;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends GenericRepository<Book> {

    List<Book> findByBookTitle(String bookTitle);

    List<Book> findByBookTitleAndStoragePlace(String bookTitle, String storagePlace);

    List<Book> findByBookTitleOrStoragePlace(String bookTitle, String storagePlace);


    @Query(nativeQuery = true,
            value = """
          select distinct b.*
          from books b
          left join books_authors ba on b.id = ba.book_id
          join authors a on a.id = ba.author_id
          where b.title ilike '%' || coalesce(:title, '%') || '%'
          and cast(b.genre as char) like coalesce(:genre,'%')
          and a.fio ilike '%' || :fio || '%'
          and b.is_deleted = false
               """)
    Page<Book> searchBooks(
            @Param(value = "genre") String genre,
            @Param(value = "title") String title,
            @Param(value = "fio") String fio,
            Pageable pageable);

}


