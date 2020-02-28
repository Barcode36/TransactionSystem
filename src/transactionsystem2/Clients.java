/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionsystem2;

import java.sql.PreparedStatement;

/**
 *
 * @author dylan
 */
public class Clients {
      public void insertintoDatabase(String Clientname, String TransactionID) throws Exception   {
           String result; 
           ConnectionClass connection = new ConnectionClass();
     connection.getConnection();
        PreparedStatement ps=null;
        String sql="INSERT into Clients(Client_name_ID,Client_name) values('" + TransactionID + "','" +  Clientname + "');";
    connection.executeSQLCommand(sql);
     
 
    System.out.println("Values Inserted Successfully");
    }
}
