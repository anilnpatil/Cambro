package com.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.DynamicTableDto;

@RestController
@RequestMapping("/api/tables")
public class DynamicTableController {

    @Autowired
    private com.demo.service.DynamicTableService dynamicTableService;

    @PostMapping("/createTable")
    public String createTable(@RequestBody DynamicTableDto tableRequest) {
        return dynamicTableService.createTable(tableRequest);
    }
}