package com.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dto.ColumnDto;
import com.demo.dto.CreateTableDto;

import org.springframework.jdbc.core.JdbcTemplate;
@Service
public class CreateTableService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String createTable(CreateTableDto createTableDto) {
        StringBuilder sql = new StringBuilder("CREATE TABLE " + createTableDto.getTableName() + " (");

        for (ColumnDto column : createTableDto.getColumns()) {
            sql.append(column.getName()).append(" ");

            // Check for null or empty datatype
            if (column.getDatatype() == null || column.getDatatype().isEmpty()) {
                return "Error: Datatype for column " + column.getName() + " is missing.";
            }

            sql.append(column.getDatatype());

            if (column.getLength() != null && !column.getDatatype().equalsIgnoreCase("varchar")) {
                sql.append("(").append(column.getLength()).append(")");
            }

            if (column.isNotNull()) {
                sql.append(" NOT NULL");
            }
            if (column.isAutoIncrement()) {
                sql.append(" AUTO_INCREMENT");
            }

            if (column.getDefaultValue() != null) {
                sql.append(" DEFAULT '").append(column.getDefaultValue()).append("'");
            }

            sql.append(", ");
        }

        // Handle primary key
        if (createTableDto.getPrimaryKey() != null && !createTableDto.getPrimaryKey().isEmpty()) {
            sql.append("PRIMARY KEY (").append(createTableDto.getPrimaryKey()).append("), ");
        }

        // Removing trailing comma and space
        sql = new StringBuilder(sql.substring(0, sql.length() - 2));

        sql.append(");");

        try {
            jdbcTemplate.execute(sql.toString());
            return "Table created successfully";
        } catch (Exception e) {
            return "Error creating table: " + e.getMessage();
        }
    }
}
