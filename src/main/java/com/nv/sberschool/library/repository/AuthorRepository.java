package com.nv.sberschool.library.repository;

import com.nv.sberschool.library.model.Author;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Hidden
public interface AuthorRepository extends GenericRepository<Author> {
    //    @Query("select a from #{#entityName} a where a.authorFio like '%?1%' and a.description like '%?2'")
    @Query(value = "select * from authors where fio = ?1 and description = ?2", nativeQuery = true)
    List<Author> customQuery(String a, String b);
}


