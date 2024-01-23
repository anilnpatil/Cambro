package com.demo.mappingService;

import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class MappingService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertData(String dbName, String tableName, Map<String, Object> data) {
        // Validate dbName and tableName if necessary

        // Construct SQL query using placeholders
        String sql = String.format("INSERT INTO %s.%s (%s) VALUES (%s)",
                dbName,
                tableName,
                String.join(", ", data.keySet()),
                String.join(", ", Collections.nCopies(data.size(), "?")));

        // Bind values and execute query

        // Bind values and execute query
        jdbcTemplate.update(sql, data.values().toArray());
       
    }
}