package com.demo.dto;

import java.util.List;

public class DynamicTableDto {
    private String tableName;
    private List<Column> columns;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

    // Getters and Setters
}

