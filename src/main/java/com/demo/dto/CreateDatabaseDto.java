package com.demo.dto;

public class CreateDatabaseDto {
        private String dbName;
    
        // Constructors, getters, and setters
        public CreateDatabaseDto() {}
    
        public CreateDatabaseDto(String dbName) {
            this.dbName = dbName;
        }
    
        public String getDbName() {
            return dbName;
        }
    
        public void setDbName(String dbName) {
            this.dbName = dbName;
        }
    } 

