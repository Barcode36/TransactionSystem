/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionsystem2;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dylan
 */
public class Delete_transactionsController implements Initializable {

    @FXML
    private ConnectionClass connection = new ConnectionClass();
    @FXML
    private ComboBox TransactionID;

    public Delete_transactionsController() {
        this.TransactionID = new ComboBox();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       populateTransactions();
    }
    @FXML
    public ArrayList<String> getTransactiondata(String TransactionID) throws SQLException {

        ArrayList<String> TransactionData = new ArrayList<>();

        String sql = "SELECT * FROM Transactions WHERE TransactionID='" + TransactionID + "';";

        ResultSet result;
        result = connection.executeSQLRequestCommand(sql);

        if (result.next()) {

            TransactionData.add(result.getString("Date"));
            TransactionData.add(result.getString("Description"));
            TransactionData.add(result.getString("Amount"));
            TransactionData.add(result.getString("Clientname"));
            TransactionData.add(result.getString("TransactionID"));
          

            connection.closeConnection();
            return TransactionData;
        } else {
            connection.closeConnection();
            return null;
        }
    }
      public void Delete_Transactions(String TID) throws SQLException {

       String responseString = "";
        String sql = "SELECT * FROM Transactions WHERE TransactionID='" + TID + "';";
        ResultSet result = connection.executeSQLRequestCommand(sql);
        if (result.next()) {
            sql = "DELETE FROM Transactions WHERE TransactionID='" + TID + "';";
            connection.executeSQLCommand(sql);
        }
        connection.closeConnection();
      

    }
   @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }
        @FXML
    public void deleteTransaction() throws SQLException {
        if (TransactionID.getSelectionModel().getSelectedIndex() != -1) {
            String TCode = TransactionID.getSelectionModel().getSelectedItem().toString();

            ArrayList<String> ProvinceData = getTransactiondata(TCode);

            if (ProvinceData != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Deleting Transaction");

                String text = "Are you sure you want to delete this Transaction? \n\n"
                        + "Province/State Code: \t\t" + ProvinceData.get(0) + "\n"
                        + "Province/State Abbreviation: \t\t" + ProvinceData.get(1) + "\n"
                        + "Province/State Name: \t" + ProvinceData.get(2) + "\n"
                        + "Province/State Reference Country: \t" + ProvinceData.get(3) + "\n"
                        + "Province/State Description: \t" + ProvinceData.get(4);

                alert.setContentText(text);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                 Delete_Transactions(TransactionID.getSelectionModel().getSelectedItem().toString());

                    AlertWindowMessages.getInstance().confirmDeletionMessage("Transactions");

                }
            } 
       
    }
    }

    private void populateTransactions() {
          try {
      
      
               getTransactionID();
           
            
        } catch (SQLException ex) {
            Logger.getLogger(Delete_transactionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void getTransactionID() throws SQLException {

        String sql = "SELECT TransactionID from Transactions";
        ResultSet rs;
        rs = connection.executeSQLRequestCommand(sql);
         while (rs.next()) {
        String Transaction_ID = rs.getString(1);
           
            TransactionID.getItems().addAll(Transaction_ID);
            TransactionID.getSelectionModel().select(-1);
        }

    }
}

