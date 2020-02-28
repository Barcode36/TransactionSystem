/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionsystem2;

import transactionsystem2.ConnectionClass;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.fxml.FXML;

/**
 *
 * @author dylan
 */
public class Transactions {
      @FXML
    private ConnectionClass connection = new ConnectionClass();
        private String price;
          public Transactions() {
    }

     public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

        
         public ArrayList<String> getLP_ProvinceDData(String ClientName) throws SQLException {
              ConnectionClass connection = new ConnectionClass();
         
        ArrayList<String> ProvinceData = new ArrayList<>();

        String sql = "SELECT * FROM Transactions WHERE Clientname='" + ClientName + "';";

        ResultSet result;
        result = connection.executeSQLRequestCommand(sql);

        if (result.next()) {
            ProvinceData.add(result.getString("Date"));
            ProvinceData.add(result.getString("Description"));
            ProvinceData.add(result.getString("Amount"));
            ProvinceData.add(result.getString("Clientname"));
            ProvinceData.add(result.getString("TransactionID"));
            connection.closeConnection();
            return ProvinceData;
        } else {
            connection.closeConnection();
            return null;
        }

}
            public void insertintoDatabase(String Date, String Desription, String Amount, String Clientname, String TransactionID) throws Exception {
        ConnectionClass connection = new ConnectionClass();
        connection.getConnection();

        String sql = "INSERT into Transactions(Date,Description,Amount,Clientname,TransactionID) values('" + Date + "', '" + Desription + "', '" + Amount + "','" + Clientname + "','" + TransactionID + "');";
        connection.executeSQLCommand(sql);

        System.out.println("Values Inserted Successfully");

    }
         
}
