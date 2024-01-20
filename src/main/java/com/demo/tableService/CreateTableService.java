package com.demo.tableService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.demo.tableDto.Column;
import com.demo.tableDto.CreateTableDto;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class CreateTableService {

    private static final Logger logger = LoggerFactory.getLogger(CreateTableService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String createTable(CreateTableDto request) {
        logger.info("Attempting to create table: {}.{}", request.getSchemaName(), request.getTableName());
        if (doesTableExist(request.getSchemaName(), request.getTableName())) {
            logger.warn("Table creation aborted: Table {}.{} already exists", request.getSchemaName(), request.getTableName());
            return "Table already exists";
        }
    
        String sql = buildCreateTableQuery(request);
        try {
            jdbcTemplate.execute(sql);
            logger.info("Table created successfully: {}.{}", request.getSchemaName(), request.getTableName());
            return "Table created successfully";
        } catch (Exception e) {
            logger.error("Error in creating table {}.{}: {}", request.getSchemaName(), request.getTableName(), e.getMessage(), e);
            return "Error in creating table: " + e.getMessage();
        }
    }

    private boolean doesTableExist(String schemaName, String tableName) {
        try {
            String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";
            List<String> result = jdbcTemplate.query(sql, new Object[]{schemaName, tableName}, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("TABLE_NAME");
                }
            });

            return !result.isEmpty();
        } catch (DataAccessException e) {
            logger.error("Error checking existence of table {}.{}: {}", schemaName, tableName, e.getMessage(), e);
            return false;
        }
    }

    private String buildCreateTableQuery(CreateTableDto request) {
        StringBuilder sql = new StringBuilder("CREATE TABLE ");
        if (request.getSchemaName() != null && !request.getSchemaName().isEmpty()) {
        sql.append(request.getSchemaName()).append(".");
        }
        sql.append(request.getTableName());
        sql.append(" (");
        List<Column> columns = request.getColumns();
        StringBuilder primaryKeyColumns = new StringBuilder();
    
        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            sql.append(column.getName())
               .append(" ")
               .append(column.getDataType());
    
            if (column.getLength() != null) {
                sql.append("(").append(column.getLength()).append(")");
            }
    
            if ("primarykey".equalsIgnoreCase(column.getKey())) {
                if (primaryKeyColumns.length() > 0) {
                    primaryKeyColumns.append(", ");
                }
                primaryKeyColumns.append(column.getName());
            }
    
            if (i < columns.size() - 1) {
                sql.append(", ");
            }
        }
    
        if (primaryKeyColumns.length() > 0) {
            sql.append(", PRIMARY KEY (").append(primaryKeyColumns).append(")");
        }
    
        sql.append(");");
        return sql.toString();
    }
}
    
        