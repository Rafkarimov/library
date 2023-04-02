package com.nv.sberschool.library.controller;

import com.nv.sberschool.library.config.jwt.JWTTokenUtil;
import com.nv.sberschool.library.dto.LoginDto;
import com.nv.sberschool.library.dto.UserDto;
import com.nv.sberschool.library.mapper.UserMapper;
import com.nv.sberschool.library.model.User;
import com.nv.sberschool.library.service.UserService;
import com.nv.sberschool.library.service.userdetails.CustomUserDetailsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/users")
@Tag(name = "Пользователи",
        description = "Контроллер для работы с пользователями библиотеки")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController extends GenericController<User, UserDto> {

    private final CustomUserDetailsService customUserDetailsService;
    private final JWTTokenUtil jwtTokenUtil;
    private final UserService service;

    public UserController(
            UserService service,
            UserMapper mapper,
            CustomUserDetailsService customUserDetailsService,
            JWTTokenUtil jwtTokenUtil
    ) {
        super(service, mapper);
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.service = service;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody LoginDto loginDto) {
        Map<String, Object> response = new HashMap<>();
        UserDetails foundUser = customUserDetailsService.loadUserByUsername(loginDto.getLogin());
        if(!service.checkPassword(loginDto.getPassword(), foundUser)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ошибка авторизации!\n Неверный пароль!");
        }

        String token = jwtTokenUtil.generateToken(foundUser);
        response.put("token", token);
        response.put("username", foundUser.getUsername());
        response.put("authorities", foundUser.getAuthorities());
        return ResponseEntity.ok().body(response);
    }
}

