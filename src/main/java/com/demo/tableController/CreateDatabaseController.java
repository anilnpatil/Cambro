package com.demo.tableController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.tableDto.CreateDatabaseDto;
import com.demo.tableService.CreateDatabaseService;

@RestController
@RequestMapping("/database")
public class CreateDatabaseController {
   
    @Autowired
    private CreateDatabaseService createDatabaseService;

    @PostMapping("/createdatabase")
    public String createDatabase(@RequestBody CreateDatabaseDto databaseDTO) {
        return createDatabaseService.createDatabase(databaseDTO.getDbName());
    }
}