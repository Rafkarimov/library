package com.nv.sberschool.library;

import com.nv.sberschool.library.model.Author;
import com.nv.sberschool.library.model.Book;
import com.nv.sberschool.library.model.Genre;
import com.nv.sberschool.library.repository.AuthorRepository;
import com.nv.sberschool.library.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.*;

@SpringBootApplication
@Slf4j
public class LibraryApplication {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private AuthorRepository authorRepository;
	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

//	Hibernate: select * from authors where fio = ? and description = ?
//	@Override
//	public void run(String... args) throws Exception {
//		authorRepository.customQuery("Иван Иваныч Иванов", "Замечатльный писатель").forEach(a -> log.info("All authors for custom query: {}", a));

//		bookRepository.deleteById(14L);
//		authorRepository.deleteById(11L);
//		Book currentBook = createBook();
//		createAuthor(new HashSet<>(List.of(currentBook)));
//
//
//
//		bookRepository.findById(currentBook.getId()).ifPresent(b -> log.info("Selected by id book: {};", b));
//		authorRepository.findAll().forEach(a -> log.info("All authors: {}", a));
//		bookRepository.findByBookTitle("title2").forEach(b -> log.info("Found by bookTitle book: {};", b));
//		bookRepository.findByBookTitleAndStoragePlace("title2", "2A")
//				.forEach(b -> log.info("Found ByBookTitleAndStoragePlace book: {};", b));
//		bookRepository.findByBookTitleOrStoragePlace("title1", "2A")
//				.forEach(b -> log.info("Found findByBookTitleOrStoragePlace book: {};", b));
//	}

	private Book createBook() {
		Book book = new Book();
		book.setBookTitle("title2");
		book.setPublisDate(LocalDate.now());
		book.setPageCount(100);
		book.setAmount(10);
		book.setStoragePlace("2A");
		book.setOnlineCopyPath("uri.com");
		book.setGenre(Genre.DRAMA);
		bookRepository.save(book);
		return book;
	}

	private void createAuthor(Set<Book> books) {
		Author author = new Author();
		author.setAuthorFio("Иван Иваныч Иванов");
		author.setBirthDate(LocalDate.now().minusYears(30));
		author.setDescription("Замечатльный писатель");
		author.setBooks(books);
		authorRepository.save(author);
	}
}
