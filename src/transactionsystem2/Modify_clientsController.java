/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionsystem2;

import transactionsystem2.AlertWindowMessages;
import transactionsystem2.ConnectionClass;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dylan
 */
public class Modify_clientsController implements Initializable {
    //private Transactions Transactions = new Transactions();

    @FXML
    private TextField Clientname;

    @FXML
    private ComboBox ClientnameID;
    @FXML
    private ConnectionClass connection = new ConnectionClass();
    @FXML
    private Label TransactionCodeErrorLabel;

    private String CID, CName;

    public Modify_clientsController() {

        this.ClientnameID = new ComboBox();
        this.Clientname = new TextField();

        this.CID = " ";
         this.CName = " ";
   
        this.TransactionCodeErrorLabel = new Label();

    }

    public void getClientIDs() throws SQLException {

        String sql = "SELECT Client_name_ID from Clients";
        ResultSet rs;
        rs = connection.executeSQLRequestCommand(sql);

        while (rs.next()) {
            String TransactionID = rs.getString(1);
            System.out.print(TransactionID);
            ClientnameID.getItems().addAll(TransactionID);
            ClientnameID.getSelectionModel().select(-1);
        }

    }

    @FXML
    public ArrayList<String> getClientdata(String Clientdata) throws SQLException {

        ArrayList<String> ClientData = new ArrayList<>();

        String sql = "SELECT * FROM Clients WHERE Client_name_ID='" + Clientdata + "';";

        ResultSet result;
        result = connection.executeSQLRequestCommand(sql);

        if (result.next()) {

            ClientData.add(result.getString("Client_name_ID"));
            ClientData.add(result.getString("Client_name"));
           
            CID = result.getString("Client_name_ID");
            CName = result.getString("Client_name");
           

            connection.closeConnection();
            return ClientData;
        } else {
            connection.closeConnection();
            return null;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            getClientIDs();
        } catch (SQLException ex) {
            Logger.getLogger(Modify_TransactionController.class.getName()).log(Level.SEVERE, null, ex);
        }

       ClientnameID.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                ArrayList<String> ClientData = null;
                try {
                    ClientData = getClientdata(ClientnameID.getSelectionModel().getSelectedItem().toString());
              
                } catch (SQLException ex) {
                    Logger.getLogger(Modify_TransactionController.class.getName()).log(Level.SEVERE, null, ex);
                }

              

               

                Clientname.setText(ClientData.get(1));
                CID= ClientData.get(0);
                CName = ClientData.get(1);

            }

        });
    }

    @FXML
    private void modifyClients() throws SQLException {

        ArrayList<String> ClientData = new ArrayList<>();

        String sql = "SELECT * FROM Clients WHERE Client_name_ID='" + ClientnameID + "';";

        ResultSet result;
        result = connection.executeSQLRequestCommand(sql);

        if (result.next()) {

            ClientData.add(result.getString("Client_name_ID"));
            ClientData.add(result.getString("Client_name"));
          

            connection.closeConnection();

            if (ClientnameID.getSelectionModel().getSelectedIndex() != -1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Client");
                String text = "Are you sure you want to modify this Client? \n\n"
                        + "Old Client Definition: \n"
                  
                        + "Client Name: \t\t" + CName + "\n"
                       
                        + "\n\n"
                      
                        + "Client name: \t" + Clientname.getText();

                alert.setContentText(text);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {

                    Modify_Clients(ClientnameID.getSelectionModel().getSelectedItem().toString(),Clientname.getText());

                    AlertWindowMessages.getInstance().confirmModifyMessage("Transactions");

                  
                    CName = Clientname.getText();
                    CID = ClientnameID.getSelectionModel().getSelectedItem().toString();

                    //AlertWindowMessages.getInstance().errorModifyingMessage("Transaction");
                }
            } else {
                connection.closeConnection();

            }

        } else {
            AlertWindowMessages.getInstance().noObjectSelectedMessage("Client");
        }

    }

    public void Modify_Clients(String CID, String TClientname) throws SQLException {

        String sql = "UPDATE `Clients` SET `Client_name`= '" + TClientname + "' WHERE `Client_name_ID`='" + CID + "';";
        connection.executeSQLCommand(sql);

        //Check Record Absence or Existence in the primary file;
        connection.closeConnection();

    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

}
