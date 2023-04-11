package com.nv.sberschool.library.dto;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Поле не должно быть пустым")
    private String authorFio;
    private String birthDate;
    @NotBlank(message = "Поле не должно быть пустым")
    private String description;
    private Set<Long> booksId;
}
