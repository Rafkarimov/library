package com.nv.sberschool.library.MVC.controller;

import static com.nv.sberschool.library.constants.UserRolesConstants.ADMIN;

import com.nv.sberschool.library.dto.UserDto;
import com.nv.sberschool.library.mapper.UserMapper;
import com.nv.sberschool.library.model.User;
import com.nv.sberschool.library.service.UserService;
import com.nv.sberschool.library.service.userdetails.CustomUserDetails;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        if (userDto.getLogin().equals(ADMIN) || service.getUserByLogin(userDto.getLogin()) != null) {
            bindingResult.rejectValue("login", "error.login", "Такой логин уже существует");
            return "redirect:/users";
        }
        if (service.getUserByEmail(userDto.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.email", "Такая почта уже существует");
            return "redirect:/users";
        }
        service.createLibrarian(mapper.toEntity(userDto));
        return "redirect:/users";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") UserDto userDto, BindingResult bindingResult) {
        if (userDto.getLogin().equals(ADMIN) || service.getUserByLogin(userDto.getLogin()) != null) {
            bindingResult.rejectValue("login", "error.login", "Такой логин уже существует");
            return "redirect:/login";
        }
        if (service.getUserByEmail(userDto.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.email", "Такая почта уже существует");
            return "redirect:/login";
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

    @GetMapping("remember-password")
    public String rememberPassword() {
        return "users/rememberPassword";
    }

    @PostMapping("remember-password")
    public String rememberPassword(@ModelAttribute("changePasswordForm") UserDto userDto) {
        service.sendChangePasswordEmail(service.getUserByEmail(userDto.getEmail()));
        return "redirect:/login";
    }

    @GetMapping("/change-password")
    public String changePassword(@PathParam(value = "uuid") String uuid, Model model) {
        model.addAttribute("uuid", uuid);
        return "users/changePassword";
    }

    @PostMapping("change-password")
    public String changePassword(
            @PathParam(value = "uuid") String uuid,
            @ModelAttribute("changePasswordForm") UserDto userDto
    ) {
        service.changePassword(uuid, userDto.getPassword());
        return "redirect:/login";
    }

}
