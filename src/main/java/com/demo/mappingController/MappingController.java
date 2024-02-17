package com.demo.mappingController;
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
    private MappingService dataService;
    @CrossOrigin("*")
    // @PostMapping("/insertData")
    // public ResponseEntity<String> insertData(@RequestParam String dbName,
    //                                          @RequestParam String tableName,
    //                                          @RequestBody List<Map<String, Object>> data) {

    //     try {
    //         // System.out.println(tableName);
    //         // System.out.println(dbName);
    //         // System.out.println(data);
            
    //        for(Map<String, Object> data2:  data){
    //             dataService.insertData(dbName, tableName, data2); 
    //         }
         
    //         return ResponseEntity.ok("Data inserted successfully");
    //     } catch (IllegalArgumentException e) {
    //         return ResponseEntity.badRequest().body(e.getMessage());
    //     } catch (Exception e) {
    //         // In production, you might want to log this error as well
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    //     }
    // }
      
    @PostMapping("/insertData")
    public ResponseEntity<ApiResponse1> insertData(@RequestParam String dbName,
                                                  @RequestParam String tableName,
                                                  @RequestBody List<Map<String, Object>> data) {

                                           
        try {
            for (Map<String, Object> data2 : data) {
                dataService.insertData(dbName, tableName, data2);
            }
            return ResponseEntity.ok(ApiResponse1.success("Data inserted successfully", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse1.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse1.error("Internal server error"));
        }
    }


    @CrossOrigin("*")
    @PostMapping("/saveSchedulId")
    public ResponseEntity<ApiResponse<String>> saveScheduleID(@RequestParam String scheduleID) {
       
        try {
            // Assuming that your dataService.saveScheduleID may throw exceptions
            String result=dataService.saveScheduleID(scheduleID);
            ApiResponse<String> response = new ApiResponse<>(true, result, null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(false, null, e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }                                                                      
     @CrossOrigin("*")
     @GetMapping("/getAllScheduleId")
    public List<Map<String, String>> getAllScheduleIDs() {
       
        return dataService.getAllScheduleIDs();
    }


    
}
