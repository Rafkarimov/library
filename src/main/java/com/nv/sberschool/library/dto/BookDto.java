package com.nv.sberschool.library.dto;

import com.nv.sberschool.library.model.Genre;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto extends GenericDto {

    private String bookTitle;
    private LocalDate publishDate;
    private Integer pageCount;
    private Integer amount;
    private String storagePlace;
    private String onlineCopyPath;
    private Genre genre;
    private Set<Long> authorsIds;
}




