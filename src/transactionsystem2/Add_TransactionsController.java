/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionsystem2;

import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import transactionsystem2.Transactions;

/**
 * FXML Controller class
 *
 * @author dylan
 */
public class Add_TransactionsController implements Initializable {

   //objects that are needed for creation of Provinces and items for Comboboxes;
   private Transactions Object = new Transactions();
    //private LP_CountryD_OT countryObject = new LP_CountryD_OT();
    //private LP_ProvinceD_OT SeqnoObject = new LP_ProvinceD_OT();
    @FXML
    private TextField Date, Description, Amount,Clientname,Transaction_ID;

    //@FXML
  //  private TextArea ProvDescTA;

    @FXML
    private ComboBox ProvRefCountryCB;

    public Add_TransactionsController() {
        this.Date = new TextField();
        this.Description = new TextField();
        this.Amount = new TextField();
        this.Clientname = new TextField();
      this.Transaction_ID = new TextField();
//        this.ProvDescTA = new TextArea();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

    @FXML
    private void addProvince() throws SQLException, Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Province Definition");
        String text = "Are you sure you want to add this province definition? \n\n"
                + "Date: \t\t\t" + Date.getText() + "\n"
                + "Description: \t\t" + Description.getText() + "\n"
                + "Amount: \t\t\t" + Amount.getText() + "\n"
                + "Client name: \t" + Clientname.getText() + "\n"
                + "Transaction: \t\t" + Transaction_ID.getText()  + "\n";

        alert.setContentText(text);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
        Object.insertintoDatabase(Date.getText(), Description.getText(), Amount.getText(),Clientname.getText(), Transaction_ID.getText());

               
         Date.clear();
               Description.clear();
                Amount.clear();
                Clientname.clear();
                Transaction_ID.clear();
               // ProvRefCountryCB.getSelectionModel().select(0);
                //ProvDescTA.clear();
                
        }
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }  
    
}
