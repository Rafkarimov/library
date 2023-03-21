package com.nv.sberschool.library.controller;

import com.nv.sberschool.library.dto.UserDto;
import com.nv.sberschool.library.mapper.UserMapper;
import com.nv.sberschool.library.model.User;
import com.nv.sberschool.library.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи",
        description = "Контроллер для работы с пользователями библиотеки")
public class UserController extends GenericController<User, UserDto> {
    public UserController(UserService service, UserMapper mapper) {
        super(service, mapper);
    }
}



