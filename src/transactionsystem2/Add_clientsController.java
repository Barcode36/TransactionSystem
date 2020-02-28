/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionsystem2;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dylan
 */
public class Add_clientsController implements Initializable {

  private Clients Object = new Clients();
    //private LP_CountryD_OT countryObject = new LP_CountryD_OT();
    //private LP_ProvinceD_OT SeqnoObject = new LP_ProvinceD_OT();
    @FXML
    private TextField Clientname,Client_ID;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
     @FXML
    private void addClients() throws SQLException, Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Province Definition");
        String text = "Are you sure you want to add this province definition? \n\n"
              
                + "Client name: \t" + Clientname.getText() + "\n"
                + "Client ID: \t\t" + Client_ID.getText()  + "\n";

        alert.setContentText(text);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
         Object.insertintoDatabase(Clientname.getText(),Client_ID.getText());

       
                Clientname.clear();
                Client_ID.clear();;
               // ProvRefCountryCB.getSelectionModel().select(0);
                //ProvDescTA.clear();

        }
    
}
    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }  
}
