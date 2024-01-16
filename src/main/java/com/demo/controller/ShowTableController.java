package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.service.ShowTableService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ShowTableController {

    @Autowired
    private ShowTableService tableService;

    @GetMapping("/database/{dbName}")
    public List<Map<String, Object>> getAllTables(@PathVariable("dbName") String databaseName) {
        return tableService.getAllTables(databaseName);
    }
}
