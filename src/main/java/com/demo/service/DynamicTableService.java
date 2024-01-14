        package com.demo.service;
		import com.demo.dto.DynamicTableDto;
		import com.demo.dto.Column;
        import java.util.List;
        import org.springframework.beans.factory.annotation.Autowired;
		import org.springframework.jdbc.core.JdbcTemplate;
		import org.springframework.stereotype.Service;

		@Service
		public class DynamicTableService {

		    @Autowired
		    private JdbcTemplate jdbcTemplate;

		    public String createTable(DynamicTableDto request) {
		        String sql = buildCreateTableQuery(request);
		       // System.out.println(sql);
		        try {
		            jdbcTemplate.execute(sql);
		            return "Table created successfully";
		        } catch (Exception e) {
		            e.printStackTrace();
		            return "Error in creating table: " + e.getMessage();
		        }
		    }

		private String buildCreateTableQuery(DynamicTableDto request) {
				StringBuilder sql = new StringBuilder("CREATE TABLE ");
				sql.append(request.getTableName());
				sql.append(" (");

				List<Column> columns = request.getColumns();
				StringBuilder primaryKeyColumns = new StringBuilder();

				for (int i = 0; i < columns.size(); i++) {
					Column column = columns.get(i);
					sql.append(column.getName())
					.append(" ")
					.append(column.getDataType());

					if (column.getLength() != null) {
						sql.append("(").append(column.getLength()).append(")");
					}

				if ("primarykey".equalsIgnoreCase(column.getKey())) {
					if (primaryKeyColumns.length() > 0) {
						primaryKeyColumns.append(", ");
					}
					primaryKeyColumns.append(column.getName());
				}

				if (i < columns.size() - 1) {
					sql.append(", ");
				}
			}

			if (primaryKeyColumns.length() > 0) {
				sql.append(", PRIMARY KEY (").append(primaryKeyColumns).append(")");
			}

			sql.append(");");
			return sql.toString();
        }
		
	}