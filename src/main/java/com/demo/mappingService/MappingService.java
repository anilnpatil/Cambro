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

    //private final Set<String> allowedTableNames = Set.of("table1", "table2"); // Add your allowed table names here

    public void insertData(String tableName, Map<String, Object> data) {
        // // Validate table name
        // if (allowedTableNames.contains(tableName)) {
        //     throw new IllegalArgumentException("Invalid table name");
        // }

        // Construct SQL query using placeholders
        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)",
                tableName,
                String.join(", ", data.keySet()),
                String.join(", ", Collections.nCopies(data.size(), "?")));
   
        // Bind values and execute query
        jdbcTemplate.update(sql, data.values().toArray());
       
    }
}