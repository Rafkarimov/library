package com.nv.sberschool.library.dto;

import com.nv.sberschool.library.model.Genre;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookSearchDTO {
    private String bookTitle;
    private String authorFio;
    private Genre genre;
}

