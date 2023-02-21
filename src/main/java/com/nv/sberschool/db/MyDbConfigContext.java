package com.nv.sberschool.db;

import com.nv.sberschool.db.dao.BookDaoJdbc;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;

@Configuration
public class MyDbConfigContext {

    @Value("${local.db.url}")
    private String url;
    @Value("${local.db.user}")
    private String user;
    @Value("${local.db.pass}")
    private String password;

    @Bean("qwe")
    @Scope("singleton")
    public DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        return dataSource;
    }

//    @Bean("myName")
//    public BookDaoJdbc bookDaoJdbc() {
//        return new BookDaoJdbc(dataSource());
//    }
}

