package com.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void registerUser(RegistrationRequest registrationRequest, String dbName, String tableName) {
        String sql = "INSERT INTO " + dbName + "." + tableName + " (username, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, registrationRequest.getUsername(), registrationRequest.getPassword());
    }

//     public boolean loginUser(LoginRequest loginRequst, String dbName, String tableName) throws SQLException {
//         String username = loginRequst.getUsername();
//         String password = loginRequst.getPassword();
//         Connection connection = null;
//         PreparedStatement statement = null;
//         ResultSet resultSet = null;

//         try {
//             // Establishing a connection to the database
//             connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "mysql");

//             // SQL query to retrieve the user
//             String sql = "SELECT password FROM "+ dbName +"." + tableName + " WHERE username =" + username + " AND password =" + password;
//             statement = ((java.sql.Connection) connection).prepareStatement(sql);
//             statement.setString(1, username);

//             // Executing the query
//             resultSet = statement.executeQuery();

//             // Checking if user exists and password matches
//             if (resultSet.next()) {
//                 String storedPassword = resultSet.getString("password");
//                 return storedPassword.equals(password);
//             }
//             return false;
//         }finally {
//             ((Statement) connection).close();
//        }
// }
}
