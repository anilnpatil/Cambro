package com.demo.mappingController;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.mappingService.MappingService;
@RestController
@RequestMapping("/mapping")
public class MappingController {

    @Autowired
    private MappingService dataService;

    @PostMapping("/insertData")
    public ResponseEntity<String> insertData(@RequestParam String dbName,
                                             @RequestParam String tableName,
                                             @RequestBody Map<String, Object> data) {
        try {
            dataService.insertData(dbName, tableName, data);
            return ResponseEntity.ok("Data inserted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // In production, you might want to log this error as well
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
}