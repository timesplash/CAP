package org.dev.backend.CAP.service.sql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
@Service
public class ConnectionService {

    private static final String CONNECTION_URL_TEMPLATE = "jdbc:postgresql://127.0.0.1:5432/%s";

    @Value("${app.database.name}")
    private String databaseName;

    @Value("${app.database.username:postgres}")
    private String userName;

    @Value("${app.database.username:postgres}")
    private String password;

    @PostConstruct
    public void setUp() {
        log.info("Database name: " + databaseName);
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                String.format(CONNECTION_URL_TEMPLATE, databaseName),
                userName,
                password
        );
    }
}

