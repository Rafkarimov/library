package com.nv.sberschool.library.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookWithAuthorsDto extends BookDto{
    private List<AuthorDto> authors;
}

