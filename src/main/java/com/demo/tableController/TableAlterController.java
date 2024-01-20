package com.demo.tableController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.tableDto.AlterTableDto;
import com.demo.tableService.TableAlterService;

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



   

