package com.nv.sberschool.library.dto;

import lombok.Data;

@Data
public class RentBookDto {

    Long bookId;
    Long userId;
    Integer amount;
    Integer rentPeriod;

}
