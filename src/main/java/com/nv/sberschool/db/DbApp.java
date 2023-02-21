package com.nv.sberschool.db;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbApp {
    private static final DbApp INSTANCE = new DbApp();

    private DbApp() {
    }

    public static DbApp getInstance() {
        return INSTANCE;
    }

    public Connection newConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/local_db",
                "postgres",
                "12345");
    }

    public DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/local_db");
        dataSource.setUser("postgres");
        dataSource.setPassword("12345");
        return dataSource;
    }
}
