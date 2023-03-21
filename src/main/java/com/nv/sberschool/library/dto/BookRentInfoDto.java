package com.nv.sberschool.library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
}
