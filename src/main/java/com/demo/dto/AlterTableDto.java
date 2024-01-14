package com.demo.dto;

import java.util.List;

import com.demo.dto.alterTableDto.ColumnAlterationDto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AlterTableDto {
        private String tableName;
        private List<ColumnAlterationDto> alterations;
    
        // Getters and setters
    }
    
    

