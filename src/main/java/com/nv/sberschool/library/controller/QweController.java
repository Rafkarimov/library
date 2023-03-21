package com.nv.sberschool.library.controller;

import com.nv.sberschool.library.model.Book;
import com.nv.sberschool.library.model.Genre;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/qwe")
@Slf4j
@Hidden
public class QweController {

    @GetMapping(value = "/hello")
    public String hello() {
        return "Hello!";
    }

    @RequestMapping(value = "/helloResponseEntity", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> helloResponseEntity(@RequestParam(value = "str", required = false) String s) {
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Hello %s with ResponseEntity", s));
    }

    @RequestMapping(value = "/getObject", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> getObject() {
        return ResponseEntity.status(HttpStatus.OK).body(new Book());
    }

    @RequestMapping(value = "/postString", method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> postString(@RequestBody String requestBody) {
        log.info("Got: {}", requestBody);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(value = "/postObject", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> postObject(@RequestBody Book requestBody) {
        log.info("Got object: {}", requestBody);
        requestBody.setGenre(Genre.NOVEL);
        log.info("Saved to database: {}", requestBody);
        return ResponseEntity.status(HttpStatus.OK).body(requestBody);
    }

//    @Autowired
//    BookRepository bookRepository;
//
//    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Book> hello(@RequestParam(value = "id") Long id) {
//        return ResponseEntity.status(HttpStatus.OK).body(bookRepository.findById(id).get());
//    }
}
