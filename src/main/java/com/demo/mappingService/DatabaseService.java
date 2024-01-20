package com.demo.mappingService;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.demo.config.DynamicDatabaseConfig;

@Service
public class DatabaseService {

    public void performDatabaseOperation(String dbName /* other parameters */) {
        DataSource dataSource = DynamicDatabaseConfig.getDataSource(dbName);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        
        // Use jdbcTemplate for database operations

    }
    
}