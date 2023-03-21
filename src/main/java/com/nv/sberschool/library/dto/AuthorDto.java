package com.nv.sberschool.library.dto;

import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto extends GenericDto {
    private String authorFio;
    private LocalDate birthDate;
    private String description;
    private Set<Long> booksId;
}

