package com.demo.mappingService;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.exception.SQLGrammarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class MappingService {
    private static final Logger logger = LoggerFactory.getLogger(MappingService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertData(String dbName, String tableName, Map<String, Object> data) throws SQLException {
        try {
            // Validate dbName and tableName if necessary

            // Construct SQL query using placeholders
            String sql = String.format("INSERT INTO %s.%s (%s) VALUES (%s)", dbName, tableName,
                    String.join(", ", data.keySet()),
                    String.join(", ", Collections.nCopies(data.size(), "?")));

            // Bind values and execute query
            jdbcTemplate.update(sql, data.values().toArray());
            logger.info("Data inserted successfully into table '{}'", tableName);
        } catch (SQLGrammarException e) {
            logger.error("SQL Error occurred while inserting data into table '{}': {}", tableName, e.getMessage(), e);
            throw e; // Propagate the exception back to the controller
                } catch (Exception e) {
            logger.error("Error occurred while inserting data into table '{}': {}", tableName, e.getMessage(), e);
            throw e; // Propagate the exception back to the controller
        }
    }

    // scheduleId Save feature
    public String saveScheduleID(String scheduleID) {
        try {
        String tableName = "boxes.scheduleid";
        String sql = String.format("INSERT INTO %s (scheduleID) VALUES (?)", tableName);

        // Execute the query using JdbcTemplate
        jdbcTemplate.update(sql, scheduleID);
        logger.info("Updated scheduleId {} Saved Successfully {}", scheduleID);
        return "scheduleId Saved Successfully";
    } catch (DataAccessException e) {
        logger.error("Error in saving scheduleId {} in database {}: {}", e.getMessage(), e);
        return "Error in Saving the dat: " + e.getMessage();
    }

    }
   //to get the scheduleID
    public List<Map<String, String>> getAllScheduleIDs() {
        //String tableName = "scheduledb.scheduleid";
        String sql = "SELECT scheduleID FROM boxes.scheduleid";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Map<String, String> scheduleId = new HashMap<>();
            scheduleId.put("name", rs.getString(1));
            return scheduleId;
        });
    }
}