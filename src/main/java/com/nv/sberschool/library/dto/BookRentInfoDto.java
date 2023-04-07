package com.nv.sberschool.library.dto;

import com.nv.sberschool.library.model.Book;
import com.nv.sberschool.library.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRentInfoDto extends GenericDto {

    private Long bookId;
    private Long userId;
    private LocalDate rentDate;
    private LocalDate returnDate;
    private boolean returned;
    private Integer rentPeriod;
    private Integer amount;
}

