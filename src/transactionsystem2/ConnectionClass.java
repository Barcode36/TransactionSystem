/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionsystem2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;


public class ConnectionClass {
    private com.mysql.jdbc.jdbc2.optional.MysqlDataSource dataSource = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
    
    private String username = "xxxxxx_xxxxx";
    private String password = "xxxxxxxxxxxxxxxx";
    private Connection connection = null;

    public void setConnection() {
        dataSource.setServerName("xxxxxx.xxxxx.xxxx");
           dataSource.setDatabaseName("xxxxxx_xxxxxx");
    }

    public boolean getConnection() {
        setConnection();
        try {
            connection = dataSource.getConnection("uzaqleuw_june","3Hotdogs!");

            System.out.println("Connection Made");

            return true;
        } catch (SQLException e) {
            System.out.println("Error getting a connection to the database: " + e.toString());
            return false;
        }
    }
   public boolean closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection Closed");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error closing the connection: " + e.toString());
            return true;
        }

        return false;
    }

    public void executeSQLCommand(String sql) {

        try {
            if (connection == null || connection.isClosed()) {
                getConnection();
            }
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error executing the SQL command: " + e.toString());
        }
    }

    public ResultSet executeSQLRequestCommand(String sql) {

        try {
            if (connection == null || connection.isClosed()) {
                getConnection();
            }
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            return result;
        } catch (SQLException e) {
            System.out.println("Error executing the request command: " + e.toString());
            return null;
        }
    } 
}
