package com.demo.mappingService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.mysql.cj.jdbc.DatabaseMetaData;

// @Service
// public class MappingService {
//     private static final Logger logger = LoggerFactory.getLogger(MappingService.class);


//     @Autowired
//     private JdbcTemplate jdbcTemplate;

//     public void insertData(String dbName, String tableName, Map<String, Object> data) {
        
//         String sql = String.format("INSERT INTO %s.%s (%s) VALUES (%s)", dbName , tableName ,
//                 String.join(", ", data.keySet()), 
//                 String.join(", ", Collections.nCopies(data.size(), "?")));
              
//         jdbcTemplate.update(sql, data.values().toArray());
       
//     }

@Service
public class MappingService {
    private static final Logger logger = LoggerFactory.getLogger(MappingService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertData(String dbName, String tableName, Map<String, Object> data) {
        try {
            // Validate dbName and tableName if necessary

            // Construct SQL query using placeholders
            String sql = String.format("INSERT INTO %s.%s (%s) VALUES (%s)", dbName, tableName,
                    String.join(", ", data.keySet()),
                    String.join(", ", Collections.nCopies(data.size(), "?")));

            // Bind values and execute query
            jdbcTemplate.update(sql, data.values().toArray());
            logger.info("Data inserted successfully into table '{}'", tableName);
        } catch (Exception e) {
            logger.error("Error occurred while inserting data into table '{}': {}", tableName, e.getMessage(), e);
        
        }
    }

    public String saveScheduleID(String scheduleID) {
        try {
        String tableName = "scheduledb.scheduleid";
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

    public List<Map<String, String>> getAllScheduleIDs() {
        //String tableName = "scheduledb.scheduleid";
        String sql = "SELECT scheduleID FROM scheduledb.scheduleid";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Map<String, String> scheduleId = new HashMap<>();
            scheduleId.put("name", rs.getString(1));
            return scheduleId;
        });
    }

    public List<String> findMatchingTables(List<Map<String, Object>> jsonObjectList) {
        List<String> matchingTables = new ArrayList<>();
        try {
            java.sql.DatabaseMetaData metaData = jdbcTemplate.getDataSource().getConnection().getMetaData();
            ResultSet tables = metaData.getTables(null, null, null, new String[] { "TABLE" });

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                ResultSet columns = metaData.getColumns(null, null, tableName, null);

                if (hasMatchingColumns(columns, jsonObjectList.get(0).keySet())) {
                    matchingTables.add(tableName);
                }
            }
        } catch (SQLException e) {
            // Handle exception
            e.printStackTrace();
        }
        return matchingTables;
    }

    private boolean hasMatchingColumns(ResultSet columns, Set<String> jsonKeys) throws SQLException {
        Set<String> columnNames = new HashSet<>();
        while (columns.next()) {
            columnNames.add(columns.getString("COLUMN_NAME"));
        }
        return columnNames.equals(jsonKeys);
    }
}



