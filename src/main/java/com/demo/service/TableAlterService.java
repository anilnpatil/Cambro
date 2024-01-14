package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.demo.dto.AlterTableDto;
import com.demo.dto.alterTableDto.ColumnAlterationDto;

@Service
public class TableAlterService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String alterTable(AlterTableDto alterTableDto) {
        String tableName = alterTableDto.getTableName();

        for (ColumnAlterationDto alteration : alterTableDto.getAlterations()) {
            StringBuilder sql = new StringBuilder("ALTER TABLE ").append(tableName).append(" ");

            if (alteration.getDropColumn() != null && alteration.getDropColumn()) {
                sql.append("DROP COLUMN ").append(alteration.getColumnName());
            } else {
                if (alteration.getNewColumnName() != null) {
                    sql.append("RENAME COLUMN ").append(alteration.getColumnName())
                        .append(" TO ").append(alteration.getNewColumnName());
                } else if (alteration.getNewColumnType() != null) {
                    sql.append("MODIFY COLUMN ").append(alteration.getColumnName())
                        .append(" ").append(alteration.getNewColumnType());
                    if (alteration.getNewColumnLength() != null) {
                        sql.append("(").append(alteration.getNewColumnLength()).append(")");
                    }
                }
            }

            jdbcTemplate.execute(sql.toString());
        }

        return "Table altered successfully";
    }
}
