package com.nv.sberschool.library.controller;

import com.nv.sberschool.library.model.User;
import com.nv.sberschool.library.repository.UserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController extends GenericController<User> {
    public UserController(UserRepository userRepository) {
        super(userRepository);
    }
}

