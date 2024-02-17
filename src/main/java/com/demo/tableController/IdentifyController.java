package com.demo.tableController;

import com.demo.tableService.DynamicTableService;
import com.demo.tableService.ShowDatabaseService;
import com.demo.tableService.ShowTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class IdentifyController {

    @Autowired
    private ShowDatabaseService databaseService;

    @Autowired
    private ShowTableService tableService;

    @Autowired
    private DynamicTableService dynamicTableService;

    @PostMapping("/identify")
    public ResponseEntity<Object> identifyTableAndDatabase(@RequestBody Map<String, String> jsonInput) {
        try {
            List<Map<String, String>> allDatabases = this.databaseService.getAllDatabases();
            HashMap<String, List<String>> databaseTables = new HashMap<>();
            HashMap<String, Set<String>> tableColumns = new HashMap<>();
            HashMap<String, String> tableDatabase = new HashMap<>();

            // Populate the map with database names and associated table names
            allDatabases.forEach(database -> {
                String databaseName = database.get("name");
                List<Map<String, String>> tables = this.tableService.getAllTables(databaseName);
                List<String> tableNames = tables.stream().map(table -> table.get("name")).collect(Collectors.toList());
                databaseTables.put(databaseName, tableNames);
                tableNames.forEach(tableName -> tableDatabase.put(tableName, databaseName));

                // Populate the map with table names and associated columns
                tables.forEach(table -> {
                    String tableName = table.get("name");
                    List<Map<String, String>> columns = this.dynamicTableService.getTableColumns(databaseName, tableName);
                    Set<String> columnNames = columns.stream().map(column -> column.get("columnName")).collect(Collectors.toSet());
                    tableColumns.put(tableName, columnNames);
                });
            });

            Set<String> jsonColumnNames = jsonInput.keySet();

            for (Map.Entry<String, Set<String>> entry : tableColumns.entrySet()) {
                String tableName = entry.getKey();
                Set<String> columnNames = entry.getValue();

                if (columnNames.equals(jsonColumnNames)) {
                    // Match found
                    Map<String, String> result = new HashMap<>();
                    result.put("tableName", tableName);
                    result.put("databaseName", tableDatabase.get(tableName));
                    return ResponseEntity.ok(result);
                }
            }

            // No match found
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Database or table not found for the given JSON input.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Return an appropriate response
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred while processing the request.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
