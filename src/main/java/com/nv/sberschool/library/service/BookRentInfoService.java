package com.nv.sberschool.library.service;

import com.nv.sberschool.library.dto.RentBookDto;
import com.nv.sberschool.library.model.Book;
import com.nv.sberschool.library.model.BookRentInfo;
import com.nv.sberschool.library.model.User;
import com.nv.sberschool.library.repository.BookRentInfoRepository;
import com.nv.sberschool.library.repository.BookRepository;
import com.nv.sberschool.library.repository.GenericRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BookRentInfoService extends GenericService<BookRentInfo>{
    private final UserService userService;
    private final BookService bookService;
    private final BookRepository bookRepository;
    private final BookRentInfoRepository repository;

    protected BookRentInfoService(UserService userService, BookService bookService,
                                  BookRepository bookRepository, BookRentInfoRepository repository) {
        super(repository);
        this.userService = userService;
        this.bookService = bookService;
        this.bookRepository = bookRepository;
        this.repository = repository;
    }

    public List<BookRentInfo> getUserBookRentInfo(Long id) {
        return userService.getOne(id).getBookRentInfos().stream().toList();
    }

    public BookRentInfo rentBook(RentBookDto rentBookDto) {
        User user = userService.getOne(rentBookDto.getUserId());
        Book book = bookService.getOne(rentBookDto.getBookId());
        if(book.getAmount() - rentBookDto.getAmount() < 0) {
            return null;
        }
        book.setAmount(book.getAmount() - rentBookDto.getAmount());
        bookService.update(book);
        BookRentInfo bookRentInfo = BookRentInfo.builder()
                .rentDate(LocalDate.now())
                .returned(false)
                .amount(rentBookDto.getAmount())
                .returnDate(LocalDate.now().plusDays(rentBookDto.getRentPeriod()))
                .rentPeriod(rentBookDto.getRentPeriod())
                .user(user)
                .book(book)
                .build();
        return repository.save(bookRentInfo);

    }

    public void returnBook(Long id) {
        BookRentInfo bookRentInfo = getOne(id);
        bookRentInfo.setReturned(true);
        bookRentInfo.setReturnDate(LocalDate.now());
        Book book = bookRentInfo.getBook();
        book.setAmount(book.getAmount() + bookRentInfo.getAmount());
        update(bookRentInfo);
        bookService.update(book);
    }
}

