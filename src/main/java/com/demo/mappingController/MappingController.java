package com.demo.mappingController;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.demo.mappingService.MappingService;
import com.demo.tableController.ApiResponse;
import com.demo.util.ApiResponse1;

@RestController
@RequestMapping("/mapping")
public class MappingController {

    @Autowired
    private MappingService mappingService;
    
    // to insert json object data into the specified db and table
    @CrossOrigin("*")
    @PostMapping("/insertData")
    public ResponseEntity<ApiResponse1> insertData(@RequestParam String dbName,
                                                   @RequestParam String tableName,
                                                   @RequestBody List<Map<String, Object>> data) {
        try {
            for (Map<String, Object> data2 : data) {
                mappingService.insertData(dbName, tableName, data2);
            }
            return ResponseEntity.ok(ApiResponse1.success("Data inserted successfully", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse1.error(e.getMessage()));
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse1.error("SQL error occurred table not matched"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse1.error("Internal server error table not matched"));
        }
    }

    //to save the schedule id
    @CrossOrigin("*")
    @PostMapping("/saveSchedulId")
    public ResponseEntity<ApiResponse<String>> saveScheduleID(@RequestParam String scheduleID) {
       
        try {
            // Assuming that your dataService.saveScheduleID may throw exceptions
            String result=mappingService.saveScheduleID(scheduleID);
            ApiResponse<String> response = new ApiResponse<>(true, result, null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(false, null, e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }  
    //to show the all schedule id                                                                    
     @CrossOrigin("*")
     @GetMapping("/getAllScheduleId")
    public List<Map<String, String>> getAllScheduleIDs() {
       
        return mappingService.getAllScheduleIDs();
    }
}
