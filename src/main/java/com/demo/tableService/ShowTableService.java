package com.demo.tableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

 @Service
public class ShowTableService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, String>> getAllTables(String databaseName) {
        // Use the database
        jdbcTemplate.execute("USE " + databaseName + ";");

        // Query to show all tables
        String sql = "SHOW TABLES;";
        List<Map<String, Object>> tables = jdbcTemplate.queryForList(sql);

        // Transform the result
        return tables.stream()
                .map(map -> map.values().stream().findFirst()
                        .map(tableName -> Map.of("name", tableName.toString()))
                        .orElse(Map.of()))
                .collect(Collectors.toList());
    }
}