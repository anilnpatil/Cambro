package com.demo.dto;

import java.util.List;

public class CreateTableDto {
        private String tableName;
        private List<ColumnDto> columns;
        private String primaryKey;
    
        // Getters and setters
        public String getTableName() {
            return tableName;
        }
    
        public void setTableName(String tableName) {
            this.tableName = tableName;
        }
    
        public List<ColumnDto> getColumns() {
            return columns;
        }
    
        public void setColumns(List<ColumnDto> columns) {
            this.columns = columns;
        }
    
        public String getPrimaryKey() {
            return primaryKey;
        }
    
        public void setPrimaryKey(String primaryKey) {
            this.primaryKey = primaryKey;
        }
    }
    
