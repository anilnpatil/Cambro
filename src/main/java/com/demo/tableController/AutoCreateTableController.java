package com.demo.tableController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.tableService.AutoCreateTableService;



@RestController
public class AutoCreateTableController {

    @Autowired
    private AutoCreateTableService autotableService;

    @PostMapping("/mapping/createTable")
    public String createTable(@RequestParam("dbName") String dbName,
                              @RequestParam("tableName") String tableName,
                              @RequestBody String jsonString) {
        return autotableService.createTable(dbName, tableName, jsonString);
    }
}