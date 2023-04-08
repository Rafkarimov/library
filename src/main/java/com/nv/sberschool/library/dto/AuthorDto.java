package com.nv.sberschool.library.dto;

import com.nv.sberschool.library.model.Book;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "Поле не должно быть пустым")
    private String authorFio;
    @NotNull(message = "Поле не должно быть пустым")
    private LocalDate birthDate;
    @NotBlank(message = "Поле не должно быть пустым")
    private String description;
    private Set<Long> booksId;
}

