package com.demo.tableService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import com.demo.tableDto.Column;
import com.demo.tableDto.DynamicTableDto;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DynamicTableService {

    private static final Logger logger = LoggerFactory.getLogger(DynamicTableService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String createTable(DynamicTableDto request) {
        logger.info("Attempting to create table: {}", request.getTableName());
        if (doesTableExist(request.getTableName())) {
            logger.warn("Table creation aborted: Table {} already exists", request.getTableName());
            return "Table already exists";
        }

        String sql = buildCreateTableQuery(request);
        try {
            jdbcTemplate.execute(sql);
            logger.info("Table created successfully: {}", request.getTableName());
            return "Table created successfully";
        } catch (Exception e) {
            logger.error("Error in creating table {}: {}", request.getTableName(), e.getMessage(), e);
            return "Error in creating table: " + e.getMessage();
        }
    }

    private boolean doesTableExist(String tableName) {
        try {
            String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?";
            List<String> result = jdbcTemplate.query(sql, new Object[]{tableName}, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getString("TABLE_NAME");
                }
            });

            return !result.isEmpty();
        } catch (DataAccessException e) {
            logger.error("Error checking existence of table {}: {}", tableName, e.getMessage(), e);
            return false;
        }
    }

    private String buildCreateTableQuery(DynamicTableDto request) {
        StringBuilder sql = new StringBuilder("CREATE TABLE ");
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
	//Delete Database feature
	public String dropDatabase(String dbName) {
        try {
            String sql = "DROP DATABASE " + dbName;
            jdbcTemplate.execute(sql);
            logger.info("Database dropped successfully: {}", dbName);
            return "Database dropped successfully";
        } catch (DataAccessException e) {
            logger.error("Error in dropping database {}: {}", dbName, e.getMessage(), e);
            return "Error in dropping database: " + e.getMessage();
        }
    }
	//delete table feature
	public String dropTable(String dbName, String tableName) {
        try {
            String sql = "DROP TABLE " + dbName + "." + tableName;
            jdbcTemplate.execute(sql);
            logger.info("Table {} dropped successfully from database {}", tableName, dbName);
            return "Table dropped successfully";
        } catch (DataAccessException e) {
            logger.error("Error in dropping table {} from database {}: {}", tableName, dbName, e.getMessage(), e);
            return "Error in dropping table: " + e.getMessage();
        }
    }
	//read the column name from the database table
	public List<Map<String, String>> getTableColumns(String dbName, String tableName) throws DataAccessException {
       
        try {
            String sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ? ORDER BY ORDINAL_POSITION";
            return jdbcTemplate.query(sql, new Object[]{dbName, tableName}, (rs, rowNum) -> {
                Map<String, String> columnMap = new HashMap<>();
                columnMap.put("columnName", rs.getString("COLUMN_NAME"));
                return columnMap;
            });
        } catch (DataAccessException e) {
            // Log or handle the exception appropriately
            throw e; // rethrowing the exception as it's better to propagate it in this context
        }
    }
}

