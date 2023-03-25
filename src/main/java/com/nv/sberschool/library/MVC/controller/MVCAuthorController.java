package com.nv.sberschool.library.MVC.controller;

import com.nv.sberschool.library.dto.AddBookDto;
import com.nv.sberschool.library.dto.AuthorDto;
import com.nv.sberschool.library.mapper.AuthorMapper;
import com.nv.sberschool.library.model.Author;
import com.nv.sberschool.library.service.AuthorService;
import com.nv.sberschool.library.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/authors")
@Slf4j
public class MVCAuthorController {

    private final AuthorMapper authorMapper;
    private final AuthorService authorService;
    private final BookService bookService;

    public MVCAuthorController(AuthorMapper authorMapper, AuthorService authorService, BookService bookService) {
        this.authorMapper = authorMapper;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping("")
    public String getAll(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int pageSize,
            Model model
    ) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Direction.ASC, "authorFio"));
        Page<Author> authorPage = authorService.listAll(pageRequest);
        Page<AuthorDto> authorDtoPage = new PageImpl<>(authorMapper.toDtos(authorPage.getContent()), pageRequest, authorPage.getTotalElements());
        model.addAttribute("authors", authorDtoPage);
        return "authors/viewAllAuthors";
    }

    @GetMapping("/add")
    public String create() {
        return "authors/addAuthor";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("authorForm") AuthorDto authorDto) {
        authorService.create(authorMapper.toEntity(authorDto));
        return "redirect:/authors";
    }

    @GetMapping("/delete/{id}")
    public String safeDelete(@PathVariable Long id) {
        authorService.softDelete(id);
        return "redirect:/authors";
    }

    @GetMapping("/restore/{id}")
    public String restore(@PathVariable Long id) {
        authorService.restore(id);
        return "redirect:/authors";
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable Long id) {
        model.addAttribute("author", authorService.getOne(id));
        return "authors/updateAuthor";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("authorForm") AuthorDto authorDto) {
        authorService.update(authorMapper.toEntity(authorDto));
        return "redirect:/authors";
    }

    @PostMapping("/search")
    public String search(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int pageSize,
            @ModelAttribute("authorSearchForm") AuthorDto authorDto,
            Model model
    ) {
        if (!StringUtils.hasText(authorDto.getAuthorFio()) || !StringUtils.hasLength(authorDto.getAuthorFio())) {
            return "redirect:/authors";
        } else {
            PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "authorFio"));
            Page<Author> authorPage = authorService.searchAuthors(authorDto.getAuthorFio().trim(), pageRequest);
            Page<AuthorDto> authorDtoPage = new PageImpl<>(authorMapper.toDtos(authorPage.getContent()), pageRequest, authorPage.getTotalElements());
            model.addAttribute("authors", authorDtoPage);
            return "authors/viewAllAuthors";
        }
    }

    @GetMapping("/add-book/{authorId}")
    public String addBook(@PathVariable Long authorId,
                          Model model) {
        model.addAttribute("books", bookService.listAll());
        model.addAttribute("authorId", authorId);
        model.addAttribute("author", authorService.getOne(authorId).getAuthorFio());
        return "authors/addAuthorBook";
    }

    @PostMapping("/add-book")
    public String addBook(@ModelAttribute("authorBookForm") AddBookDto addBookDTO) {
        authorService.addBook(addBookDTO);
        return "redirect:/authors";
    }

}
