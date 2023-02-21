package com.nv.sberschool.db.dao;

import com.nv.sberschool.db.model.Book;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class BookDaoJdbc {

    @Qualifier("qwe")
    private final DataSource dataSource;

    public BookDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //select * from public.books where id = ?;
    public Optional<Book> findBookById(Integer id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement selectQuery = connection.prepareStatement(
                    "select * from books where id=?");
            selectQuery.setInt(1, id);
            ResultSet resultSet = selectQuery.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setDateAdded(resultSet
                        .getTimestamp("date_added")
                        .toLocalDateTime());
                return Optional.of(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Book> getAllBooks() {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        return jdbcTemplate.query("select * from books"
                , (rs, rowNum) -> {
                    Book book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setDateAdded(rs
                            .getTimestamp("date_added")
                            .toLocalDateTime());
                    return book;
                });
    }
}

