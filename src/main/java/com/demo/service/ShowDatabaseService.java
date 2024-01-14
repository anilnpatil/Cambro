package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShowDatabaseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, String>> getAllDatabases() {
        String sql = "SHOW DATABASES;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Map<String, String> database = new HashMap<>();
            database.put("name", rs.getString(1));
            return database;
        });
    }
}