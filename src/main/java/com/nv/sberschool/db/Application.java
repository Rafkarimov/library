package com.nv.sberschool.db;

import com.nv.sberschool.db.dao.BookDaoJdbc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class Application implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private final BookDaoJdbc dao;

    public Application(BookDaoJdbc dao) {
        this.dao = dao;
    }

    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        dao.findBookById(1).ifPresent(b -> log.info("Found book: {}", b));
        dao.getAllBooks().forEach(b -> log.info("Found from all books: {}", b));
    }

    //    public static void main(String[] args) {
//        BookDaoJdbc dao = new BookDaoJdbc(DbApp.getInstance().dataSource());
//        dao.findBookById(1).ifPresent(b -> log.info("Found book: {}", b));
//        dao.getAllBooks().forEach(b -> log.info("Found from all books: {}", b));
//    }

//    public static void main(String[] args) {
//        ApplicationContext ctx =
//                new AnnotationConfigApplicationContext(MyDbConfigContext.class);
//        BookDaoJdbc dao = (BookDaoJdbc) ctx.getBean("myName");
//        dao.findBookById(1).ifPresent(b -> log.info("Found book: {}", b));
//        dao.getAllBooks().forEach(b -> log.info("Found from all books: {}", b));
//    }

//    public static void main(String[] args) {
//        ApplicationContext ctx = SpringApplication.run(Application.class, args);
//        BookDaoJdbc dao = ctx.getBean(BookDaoJdbc.class);
//        dao.findBookById(1).ifPresent(b -> log.info("Found book: {}", b));
//        dao.getAllBooks().forEach(b -> log.info("Found from all books: {}", b));
//    }
}

