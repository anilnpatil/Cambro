package com.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.service.ShowDatabaseService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ShowDatabaseController {

    @Autowired
    private ShowDatabaseService databaseService;

    @GetMapping("/showdbs")
    public List<Map<String, String>> getAllDatabases() {
        return databaseService.getAllDatabases();
    }

}