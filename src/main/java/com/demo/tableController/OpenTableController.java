package com.demo.tableController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.config.DatabaseConfig;
import com.demo.tableService.OpenTableService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OpenTableController {

    @Autowired
    private OpenTableService tableDataService;

    @GetMapping("/{dbName}/table-data/{tableName}")
    public List<Map<String, Object>> getTableData(@PathVariable String dbName, @PathVariable String tableName) {
        DatabaseConfig databse=new DatabaseConfig();
        databse.setDatabaseName(dbName);
        return tableDataService.getTableData(dbName, tableName);
    }
}