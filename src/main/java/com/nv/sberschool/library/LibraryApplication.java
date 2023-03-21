package com.nv.sberschool.library;

import com.nv.sberschool.library.repository.AuthorRepository;
import com.nv.sberschool.library.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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



}

