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
public class Delete_clientsController implements Initializable {

    @FXML
    private ConnectionClass connection = new ConnectionClass();
    @FXML
    private ComboBox ClientID;

    public Delete_clientsController() {
        this.ClientID = new ComboBox();
    }

    public void getClientID() throws SQLException {

        String sql = "SELECT Client_name_ID from Clients";
        ResultSet rs;
        rs = connection.executeSQLRequestCommand(sql);
        while (rs.next()) {
            String Transaction_ID = rs.getString(1);

            ClientID.getItems().addAll(Transaction_ID);
            ClientID.getSelectionModel().select(-1);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            getClientID();

        } catch (SQLException ex) {
            Logger.getLogger(Delete_transactionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void deleteClients() throws SQLException {
        if (ClientID.getSelectionModel().getSelectedIndex() != -1) {
            String DCode = ClientID.getSelectionModel().getSelectedItem().toString();

            ArrayList<String> ClientData = getClientdata(DCode);

            if (ClientData != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Deleting Department");

                String text = "Are you sure you want to delete this department? \n\n"
                        + "Client ID: \t\t" + ClientData.get(0) + "\n"
                        + "Client Name: \t\t" + ClientData.get(1);

                alert.setContentText(text);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {
                    Delete_Clients(ClientID.getSelectionModel().getSelectedItem().toString());
                    AlertWindowMessages.getInstance().confirmDeletionMessage("Transactions");
                }

            }

        }
    }

    public void Delete_Clients(String TClientname) throws SQLException {

        String sql = "SELECT * FROM Clients WHERE Client_name_ID='" + TClientname + "';";
        ResultSet result = connection.executeSQLRequestCommand(sql);
        if (result.next()) {
            sql = "DELETE FROM Clients WHERE Client_name_ID='" + TClientname + "';";
            connection.executeSQLCommand(sql);
        }
        connection.closeConnection();

    }

    @FXML
    public ArrayList<String> getClientdata(String ClientID) throws SQLException {

        ArrayList<String> ClientData = new ArrayList<>();

        String sql = "SELECT * FROM Clients WHERE Client_name_ID='" + ClientID + "';";

        ResultSet result;
        result = connection.executeSQLRequestCommand(sql);

        if (result.next()) {

            ClientData.add(result.getString("Client_name_ID"));
            ClientData.add(result.getString("Client_name"));

            connection.closeConnection();
            return ClientData;
        } else {
            connection.closeConnection();
            return null;
        }
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }
}
