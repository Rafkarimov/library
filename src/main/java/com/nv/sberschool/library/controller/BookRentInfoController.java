package com.nv.sberschool.library.controller;

import com.nv.sberschool.library.dto.BookRentInfoDto;
import com.nv.sberschool.library.mapper.BookRentInfoMapper;
import com.nv.sberschool.library.mapper.GenericMapper;
import com.nv.sberschool.library.model.BookRentInfo;
import com.nv.sberschool.library.service.BookRentInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Set;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rent/info")
@Tag(name = "Аренда книг",
        description = "Контроллер для работы с арендой/выдачей книг пользователям библиотеки")
@SecurityRequirement(name = "Bearer Authentication")
public class BookRentInfoController extends GenericController<BookRentInfo, BookRentInfoDto> {

    private final BookRentInfoService service;
    private final BookRentInfoMapper mapper;

    public BookRentInfoController(
            BookRentInfoService service,
            BookRentInfoMapper mapper) {
        super(service, mapper);
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(description = "Просмотреть список арендованных пользователем книг", method = "getUserRentBookInfo")
    @RequestMapping(value = "/get-user-rent-book-info/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookRentInfoDto>> getUserRentBookInfo(@PathVariable Long userId) {
        return ResponseEntity.ok().body(mapper.toDtos(service.getUserBookRentInfo(userId)));
    }

}

