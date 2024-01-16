package com.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.demo.dto.AlterTableDto;
import com.demo.service.TableAlterService;

@RestController
@RequestMapping("/api/table")
public class TableAlterController {

    @Autowired
    private TableAlterService tableAlterationService;

    @PutMapping("/alter")
    public ResponseEntity<String> alterTable(@RequestBody AlterTableDto alterTableDto) {
        String response = tableAlterationService.alterTable(alterTableDto);
        return ResponseEntity.ok(response);
    }
}



   

