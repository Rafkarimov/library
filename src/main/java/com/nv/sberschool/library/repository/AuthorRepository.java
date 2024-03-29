package com.nv.sberschool.library.repository;

import com.nv.sberschool.library.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends GenericRepository<Author> {
    //    @Query("select a from #{#entityName} a where a.authorFio like '%?1%' and a.description like '%?2'")
    @Query(value = "select * from authors where fio = ?1 and description = ?2", nativeQuery = true)
    List<Author> customQuery(String a, String b);

    @Query("""
          select case when count(a) > 0 then false else true end
          from Author a join a.books b
                        join BookRentInfo bri on b.id = bri.book.id
          where a.id = :authorId
          and bri.returned = false
          """)
    boolean checkAuthorForDeletion(final Long authorId);

    Page<Author> findAllByAuthorFioContainsIgnoreCaseAndIsDeletedFalse(String fio, Pageable pageable);
}

