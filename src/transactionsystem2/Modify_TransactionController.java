package transactionsystem2;

import transactionsystem2.ConnectionClass;
import transactionsystem2.AlertWindowMessages;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Modify_TransactionController implements Initializable {

    private Transactions Transactions = new Transactions();

    @FXML
    private TextField Date, Description, Amount, Clientname;

    @FXML
    private ComboBox Transaction_ID;
    @FXML
    private ConnectionClass connection = new ConnectionClass();
    @FXML
    private Label TransactionCodeErrorLabel;

    private String TID, TDate, TCName, TAmount, TDesc;

    public Modify_TransactionController() {

        this.Date = new TextField();
        this.Description = new TextField();
        this.Amount = new TextField();
        this.Clientname = new TextField();
        this.Transaction_ID = new ComboBox();

        this.TDate = "";
        this.TCName = "";
        this.TAmount = "";
        this.TDesc = "";
        this.TID = " ";

        this.TransactionCodeErrorLabel = new Label();

    }

    public void getTransactionID() throws SQLException {

        String sql = "SELECT TransactionID from Transactions";
        ResultSet rs;
        rs = connection.executeSQLRequestCommand(sql);

        while (rs.next()) {
            String TransactionID = rs.getString(1);
            System.out.print(TransactionID);
            Transaction_ID.getItems().addAll(TransactionID);
            Transaction_ID.getSelectionModel().select(-1);
        }

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
            TDate = result.getString("Date");
            TDesc = result.getString("Description");
            TAmount = result.getString("Amount");
            TCName = result.getString("Clientname");
            TID = result.getString("TransactionID");

            connection.closeConnection();
            return TransactionData;
        } else {
            connection.closeConnection();
            return null;
        }
    }
 
    @Override
 public void initialize(URL url, ResourceBundle rb) {
        try {
            getTransactionID();
        } catch (SQLException ex) {
            Logger.getLogger(Modify_TransactionController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Transaction_ID.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                ArrayList<String> TransactionData = null;
                try {
                    TransactionData = getTransactiondata(Transaction_ID.getSelectionModel().getSelectedItem().toString());
                    System.out.println(TransactionData.get(1));
                    System.out.println(TransactionData.get(3));
                } catch (SQLException ex) {
                    Logger.getLogger(Modify_TransactionController.class.getName()).log(Level.SEVERE, null, ex);
                }

                Date.setText(TransactionData.get(0));

                TDate = TransactionData.get(0);

                Description.setText(TransactionData.get(1));
                TDesc = TransactionData.get(1);

                Amount.setText(TransactionData.get(2));
                TAmount = TransactionData.get(2);

                Clientname.setText(TransactionData.get(3));
                TID = TransactionData.get(4);
                TCName = TransactionData.get(3);

            }

        });
    }

    @FXML
    public void modifyTransactions() throws SQLException {

        ArrayList<String> TransactionData = new ArrayList<>();

        String sql = "SELECT * FROM Transactions WHERE TransactionID='" + Transaction_ID + "';";

        ResultSet result;
        result = connection.executeSQLRequestCommand(sql);

        if (result.next()) {

            TransactionData.add(result.getString("Date"));
            TransactionData.add(result.getString("Description"));
            TransactionData.add(result.getString("Amount"));
            TransactionData.add(result.getString("Clientname"));
            TransactionData.add(result.getString("TransactionID"));

            connection.closeConnection();

            if (Transaction_ID.getSelectionModel().getSelectedIndex() != -1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Country");
                String text = "Are you sure you want to modify this Transaction? \n\n"
                        + "Old Transactions Definition: \n"
                        + "Date: \t\t\t" + TDate + "\n"
                        + "Description: \t\t" + TDesc + "\n"
                        + "Amount: \t\t\t" + TAmount + "\n"
                        + "Client name: \t" + TCName + "\n"
                        + "\n\n"
                        + "New Country Definition: \n"
                        + "Date: \t\t\t" + Date.getText() + "\n"
                        + "Description: \t\t" + Description.getText() + "\n"
                        + "Amount: \t\t\t" + Amount.getText() + "\n"
                        + "Client name: \t" + Clientname.getText();

                alert.setContentText(text);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) {

                    Modify_Transactions(Transaction_ID.getSelectionModel().getSelectedItem().toString(), Date.getText(), Description.getText(), Amount.getText(), Clientname.getText());

                    AlertWindowMessages.getInstance().confirmModifyMessage("Transactions");

                    TDate = Date.getText();
                    TDesc = Description.getText();
                    TAmount = Amount.getText();
                    TCName = Clientname.getText();
                    TID = Transaction_ID.getSelectionModel().getSelectedItem().toString();

                    //AlertWindowMessages.getInstance().errorModifyingMessage("Transaction");
                }
            } else {
                connection.closeConnection();

            }

        } else {
            AlertWindowMessages.getInstance().noObjectSelectedMessage("Country");
        }

    }

    public void Modify_Transactions(String TID, String TDate, String TDescr, String TAmount, String TClientname) throws SQLException {

        String sql = "UPDATE `Transactions` SET `Date`='" + TDate + "',`Description` ='" + TDescr + "', `Amount`= '" + TAmount +  "', `Clientname`= '" + TClientname + "' WHERE `TransactionID`='" + TID + "';";
        connection.executeSQLCommand(sql);

        //Check Record Absence or Existence in the primary file;
        connection.closeConnection();

    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

}
