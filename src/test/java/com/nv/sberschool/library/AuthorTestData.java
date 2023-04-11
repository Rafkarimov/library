package com.nv.sberschool.library;

import com.nv.sberschool.library.model.Author;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public interface AuthorTestData {

    Author AUTHOR_1 = new Author("author1",
            LocalDate.now(),
            "description1",
            new HashSet<>());

    Author AUTHOR_2 = new Author("author2",
            LocalDate.now(),
            "description2",
            null);

    Author AUTHOR_3 = new Author("author3",
            LocalDate.now(),
            "description3",
            null);

    List<Author> AUTHOR_LIST = Arrays.asList(AUTHOR_1, AUTHOR_2, AUTHOR_3);
}
