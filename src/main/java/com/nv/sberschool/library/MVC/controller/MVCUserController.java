package com.nv.sberschool.library.MVC.controller;

import com.nv.sberschool.library.dto.UserDto;
import com.nv.sberschool.library.mapper.UserMapper;
import com.nv.sberschool.library.model.User;
import com.nv.sberschool.library.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.nv.sberschool.library.constants.UserRolesConstants.ADMIN;

@Controller
@RequestMapping("/users")
public class MVCUserController {

    private final UserService service;
    private final UserMapper mapper;

    public MVCUserController(UserService service, UserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/add-librarian")
    public String addLibrarian() {
        return "users/addLibrarian";
    }

    @PostMapping("/add-librarian")
    public String addLibrarian(@ModelAttribute("userForm") UserDto userDto, BindingResult bindingResult) {
        if(userDto.getLogin().equals(ADMIN) || service.getUserByLogin(userDto.getLogin()) != null) {
            bindingResult.rejectValue("login", "error.login", "Такой логин уже существует");
        }
        if(service.getUserByEmail(userDto.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.email", "Такая почта уже существует");
        }
        service.createLibrarian(mapper.toEntity(userDto));
        return "redirect:/users";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") UserDto userDto, BindingResult bindingResult) {
        if(userDto.getLogin().equals(ADMIN) || service.getUserByLogin(userDto.getLogin()) != null) {
            bindingResult.rejectValue("login", "error.login", "Такой логин уже существует");
        }
        if(service.getUserByEmail(userDto.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.email", "Такая почта уже существует");
        }
        service.create(mapper.toEntity(userDto));
        return "redirect:/login";
    }

    @GetMapping("")
    public String getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int pageSize,
            Model model
    ) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Direction.ASC, "login"));
        Page<User> userPage = service.listAll(pageRequest);
        Page<UserDto> userDtoPage = new PageImpl<>(mapper.toDtos(userPage.getContent()), pageRequest, userPage.getTotalElements());
        model.addAttribute("users", userDtoPage);
        return "users/viewAllUsers";
    }

    @GetMapping("/profile/{id}")
    public String viewProfile(@PathVariable Long id, Model model) {
        model.addAttribute("user", mapper.toDto(service.getOne(id)));
        return "profile/viewProfile";
    }

    @GetMapping("/profile/update/{id}")
    public String updateProfile(@PathVariable Long id, Model model) {
        model.addAttribute("user", mapper.toDto(service.getOne(id)));
        return "profile/updateProfile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("userForm") UserDto userDto) {
        service.update(mapper.toEntity(userDto));
        return "redirect:/users/profile/" + userDto.getId();
    }


}

