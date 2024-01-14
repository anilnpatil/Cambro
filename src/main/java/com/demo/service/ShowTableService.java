package com.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ShowTableService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> getAllTables(String databaseName) {
        // Use the database
        jdbcTemplate.execute("USE " + databaseName + ";");

        // Query to show all tables
        String sql = "SHOW TABLES;";
        return jdbcTemplate.queryForList(sql, String.class);
    }
}