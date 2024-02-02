package com.demo.mappingService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    public void saveScheduleID(String scheduleID) {
        String tableName = "scheduledb.scheduleid";
        String sql = String.format("INSERT INTO %s (scheduleID) VALUES (?)", tableName);

        // Execute the query using JdbcTemplate
        jdbcTemplate.update(sql, scheduleID);
    }
    
    public List<String> getAllScheduleIDs() {
        //String tableName = "scheduledb.scheduleid";
        String sql = String.format("SELECT scheduleID FROM scheduledb.scheduleid");

        // Execute the query and return the result as a List of Strings
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        List<String> scheduleIDs = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            scheduleIDs.add((String) row.get("scheduleID"));
        }

        return scheduleIDs;
    }

    

    
}
