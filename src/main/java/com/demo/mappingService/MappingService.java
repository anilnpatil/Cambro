package com.demo.mappingService;

import java.sql.SQLException;

import org.hibernate.exception.SQLGrammarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

import javax.transaction.Transactional;
@Service
public class MappingService {
    private static final Logger logger = LoggerFactory.getLogger(MappingService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Map<String, Object>> insertData(String dbName, String tableName, List<Map<String, Object>> dataList) throws SQLException {
        List<Map<String, Object>> insertedDataList = new ArrayList<>();
        try {
            for (Map<String, Object> data : dataList) {
                // Add starttime, endtime, and date to the data map
                LocalDateTime currentTime = LocalDateTime.now();
                data.put("starTtime", currentTime);
                data.put("endTime", null); // Assuming endtime is initially null
                data.put("date", currentDate());
    
                // Construct SQL query using placeholders
                String sql = String.format("INSERT INTO %s.%s (%s) VALUES (%s)",
                        dbName, tableName,
                        String.join(", ", data.keySet()),
                        String.join(", ", Collections.nCopies(data.size(), "?")));
    
                // Bind values and execute query
                Object[] values = data.values().toArray();
                jdbcTemplate.update(sql, values); // Pass only the data values array
                logger.info("Data inserted successfully into table '{}'", tableName);
                insertedDataList.add(data);
            }
            return insertedDataList;
        } catch (SQLGrammarException e) {
            logger.error("SQL Error occurred while inserting data into table '{}': {}", tableName, e.getMessage(), e);
            throw e; // Propagate the exception back to the controller
        } catch (Exception e) {
            logger.error("Error occurred while inserting data into table '{}': {}", tableName, e.getMessage(), e);
            throw e; // Propagate the exception back to the controller
        }
    }
    
    // Helper method to get current date
    private java.sql.Date currentDate() {
        return new java.sql.Date(Calendar.getInstance().getTime().getTime());
    }
    

    @Transactional
    public String updateEndTime(String dbName, String tableName, String scheduleID) {
        try {
            // Generate current timestamp
            LocalDateTime endTime = LocalDateTime.now();

            // Construct the SQL query
            String sql = String.format("UPDATE %s.%s SET endTime = ? WHERE scheduleOlsnReleaseBarcode = ?", dbName, tableName);

            // Execute the update query
            int rowsAffected = jdbcTemplate.update(sql, endTime, scheduleID);

            if (rowsAffected > 0) {
                return "End time updated successfully";
            } else {
                throw new RuntimeException("No rows affected. please check the entered scheduleId.");
            }
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Return an error message
            return "Failed to update end time: " + e.getMessage();
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