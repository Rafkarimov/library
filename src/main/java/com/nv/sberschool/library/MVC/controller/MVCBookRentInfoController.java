package com.nv.sberschool.library.MVC.controller;

import com.nv.sberschool.library.dto.RentBookDto;
import com.nv.sberschool.library.mapper.BookMapper;
import com.nv.sberschool.library.service.BookRentInfoService;
import com.nv.sberschool.library.service.BookService;
import com.nv.sberschool.library.service.userdetails.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rent")
public class MVCBookRentInfoController {

    private final BookService bookService;
    private final BookMapper bookMapper;
    private final BookRentInfoService service;

    public MVCBookRentInfoController(BookService bookService, BookMapper bookMapper, BookRentInfoService service) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
        this.service = service;
    }

    @GetMapping("/get-book/{bookId}")
    public String getBook(@PathVariable Long bookId, Model model) {
        model.addAttribute("book", bookMapper.toDto(bookService.getOne(bookId)));
        return "userBooks/getBook";
    }

    @PostMapping("/get-book")
    public String getBook(@ModelAttribute("publishForm") RentBookDto rentBookDto) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        rentBookDto.setUserId(Long.valueOf(customUserDetails.getUserId()));
        service.rentBook(rentBookDto);
        return "redirect:/books";
    }

    @GetMapping("/user-books")
    public String getUserBooks(Model model) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("rentBooks", service.getUserBookRentInfo(Long.valueOf(customUserDetails.getUserId())));
        return "userBooks/viewAllUserBooks";
    }

    @GetMapping("return-book/{id}")
    public String returnBook(@PathVariable Long id) {
        service.returnBook(id);
        return "redirect:/rent/user-books";
    }
}
