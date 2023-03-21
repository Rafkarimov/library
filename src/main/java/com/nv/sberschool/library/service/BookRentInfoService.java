package com.nv.sberschool.library.service;

import com.nv.sberschool.library.model.BookRentInfo;
import com.nv.sberschool.library.repository.GenericRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookRentInfoService extends GenericService<BookRentInfo>{
    private final UserService userService;

    protected BookRentInfoService(GenericRepository<BookRentInfo> repository, UserService userService) {
        super(repository);
        this.userService = userService;
    }

    public List<BookRentInfo> getUserBookRentInfo(Long id) {
        return userService.getOne(id).getBookRentInfos().stream().toList();
    }
}

