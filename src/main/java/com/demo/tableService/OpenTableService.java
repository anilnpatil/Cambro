package com.demo.tableService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class OpenTableService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getTableData(String dbName, String tableName) {
        // You should validate dbName and tableName to prevent SQL Injection
        // For example, check against a list of allowed database and table names

        String sql = "SELECT * FROM " + dbName + "." + tableName + ";";
        return jdbcTemplate.queryForList(sql);
    }
}