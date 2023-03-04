package com.nv.sberschool.library.controller;

import com.nv.sberschool.library.model.BookRentInfo;
import com.nv.sberschool.library.repository.BookRentInfoRepository;
import com.nv.sberschool.library.repository.GenericRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rentInfos")
public class BookRentInfoController extends GenericController<BookRentInfo> {
    public BookRentInfoController(BookRentInfoRepository bookRentInfoRepository) {
        super(bookRentInfoRepository);
    }
}

