package com.nv.sberschool.library.repository;

import com.nv.sberschool.library.model.BookRentInfo;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface BookRentInfoRepository extends GenericRepository<BookRentInfo> {
}


