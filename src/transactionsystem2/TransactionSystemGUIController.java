/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionsystem2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.STYLESHEET_CASPIAN;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import org.apache.poi.hpsf.Decimal;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TransactionSystemGUIController implements Initializable {
//Global Variables and FXML variables

    File file;

    Date date;
    String description;
    double amount;
    Cell cell;
    Row row;
    final JFileChooser fc = new JFileChooser();
    @FXML
    private ComboBox profits_by_client_name;
    @FXML
    private ComboBox spending_by_client_name;
    @FXML
    private Label Clients;
    @FXML
    private TextArea Description;
    @FXML
    private Button Add_Client;
    @FXML
    private Button Modify_Client;
    @FXML
    private Button Delete_Client;
    @FXML
    private Button Join_Client;
    @FXML
    private TextArea Result_Client;
    @FXML
    private Label Transactions;
    @FXML
    private Button AddCVS;
    @FXML
    private Button Total_earings_by_date;
    @FXML
    private Button Total_client_spending;
    @FXML
    private Button Add_transaction;
    @FXML
    private Button Delete_transaction;
    @FXML
    private Button Modify_transaction;
    @FXML
    private Button Join_transaction;
    @FXML
    private TextArea Results_trasactions;
//Controller

    @FXML
    private void handleButtonAction(ActionEvent event) {
        //Instantiate FXML variables
        this.Clients = new Label();
        this.Description = new TextArea();
        this.Add_Client = new Button();
        this.Modify_Client = new Button();
        this.Delete_Client = new Button();
        this.Join_Client = new Button();
        this.Result_Client = new TextArea();
        this.spending_by_client_name = new ComboBox();
        this.profits_by_client_name = new ComboBox();
        this.Transactions = new Label();
        this.AddCVS = new Button();
        this.Total_earings_by_date = new Button();
        this.Total_client_spending = new Button();
        this.Add_transaction = new Button();
        this.Delete_transaction = new Button();
        this.Modify_transaction = new Button();
        this.Join_transaction = new Button();

    }
//This function calculates client profits by specified user

    public void calculate_client_profits() throws SQLException {
        //Connects to Database
        ConnectionClass Databaseloader = new ConnectionClass();
        Databaseloader.getConnection();
        //Extract name of client from Combobox
        String test = profits_by_client_name.getValue().toString();
        //Test SQL
        String sql = "SELECT SUM(Amount) AS total FROM Transactions WHERE Clientname LIKE '%" + test + "%';";
        //Execute command
        ResultSet rs = Databaseloader.executeSQLRequestCommand(sql);
        ResultSetMetaData rsMetaData = rs.getMetaData();
        int numberOfColumns = rsMetaData.getColumnCount();
        System.out.println(numberOfColumns);
// get the column names; column indexes start from 1
        for (int i = 1; i < numberOfColumns + 1; i++) {
            String columnName = rsMetaData.getColumnName(i);

            // Get the name of the column's table name
            if ("total".equals(columnName)) {

                rs.last();
                int count = rs.getRow();
                rs.beforeFirst();
                System.out.println(count);
                while (rs.next()) {
                    Results_trasactions.setText("");
                    Results_trasactions.setText("The total profits for " + profits_by_client_name.getValue() + " today are: \n" + rs.getString(1));
                }
            }

        }
    }
    //This program calcuates client spending

    public void calculate_client_spending() throws SQLException {
        //Try this spending
        try {
            //Conenct to class
            ConnectionClass Databaseloader = new ConnectionClass();
            Databaseloader.getConnection();
            String test = spending_by_client_name.getValue().toString();
            //Select SQL
            String sql = "SELECT SUM(Amount) AS total FROM Transactions WHERE Clientname LIKE '%" + test + "%';";
            ResultSet rs = Databaseloader.executeSQLRequestCommand(sql);
            Results_trasactions.setText("");
            while (rs.next()) {
                double total = rs.getDouble(1) / .7;
                Results_trasactions.setText("");
                Results_trasactions.setText("The total profits for " + spending_by_client_name.getValue() + " today are: \n" + total);
                Results_trasactions.setText("The total client spending today is: \n" + total);
            }
        } catch (SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    public void total_spending_by_client_name() {
        String SQL = "SELECT * FROM Clients;";
        ConnectionClass Databaseloader = new ConnectionClass();
        Databaseloader.getConnection();
        ResultSet rs = Databaseloader.executeSQLRequestCommand(SQL);
        try {
            while (rs.next()) {
                int Client_name_ID = rs.getInt("Client_name_ID");
                String Client_name = rs.getString("Client_name");
                spending_by_client_name.getItems().addAll(Client_name);
                profits_by_client_name.getItems().addAll(Client_name);

            }
        } catch (SQLException ex) {
            Logger.getLogger(TransactionSystemGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spending_by_client_name.setPrefWidth(150);
        profits_by_client_name.setPrefWidth(150);
        profits_by_client_name.setPrefHeight(40);
        spending_by_client_name.setPrefHeight(40);
        spending_by_client_name.setValue("Get spending \n by client name");
        profits_by_client_name.setValue("Get profits \n by client name");
        Description.setPrefWidth(700);
        Description.setPrefHeight(50);
        Description.setText("Welcome to the Transactions Database Software. This program allows you to open an Excel file, and \n puts the data from file into and database, and allows you to manipulate the data in different ways. \n Please pick an option to manipulate the Database. ");
        Result_Client.setText("Client_name_ID \t Client_name");
        total_spending_by_client_name();
        printtransactiontable();
        printclienttable();
    }

    public void printclienttable() {

        Result_Client.setText("Client_name_ID \t Client_name");
        String SQL = "SELECT * FROM Clients;";
        ConnectionClass Databaseloader = new ConnectionClass();
        Databaseloader.getConnection();
        ResultSet rs = Databaseloader.executeSQLRequestCommand(SQL);
        try {
            while (rs.next()) {
                int Client_name_ID = rs.getInt("Client_name_ID");
                String Client_name = rs.getString("Client_name");
                Result_Client.appendText("\n" + rs.getString(1) + "\t \t " + rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransactionSystemGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void printtransactiontable() {
        try {

            ConnectionClass Databaseloader = new ConnectionClass();
            Databaseloader.getConnection();
            String SQL = "SELECT * FROM Transactions;";

            ResultSet rs = Databaseloader.executeSQLRequestCommand(SQL);
            Results_trasactions.setText("Date" + "\t" + "\t" + "Description" + "\t" + "Amount \t Client Name \t\t TransactionID");
            while (rs.next()) {
                Transactions price = new Transactions();
                String Date = rs.getString("Date");
                BigDecimal Amount = new BigDecimal(rs.getString("Amount"));
                String Clientname = rs.getString("Clientname");
                int TransactionID = rs.getInt("TransactionID");

                // print the results
                Results_trasactions.appendText("\n" + rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getBigDecimal("Amount") + "\t" + rs.getString(4) + "\t" + "\t" + rs.getInt(5));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransactionSystemGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void Delete_transaction_table() throws IOException {
        ConnectionClass connection = new ConnectionClass();
        connection.getConnection();

        String sql = "Delete From Transactions;";
        connection.executeSQLCommand(sql);
        printtransactiontable();
    }

    @FXML
    public void Delete_client_table() throws IOException {

        ConnectionClass connection = new ConnectionClass();
        connection.getConnection();

        String sql = "Delete From Clients;";
        connection.executeSQLCommand(sql);
        printclienttable();
    }

    @FXML
    public void addTransaction() throws IOException {

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Add_Transactions.fxml"));
        // root = FXMLLoader.load(getClass().getClassLoader().getResource(""));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Adding Tranaction");
        //   stage.initOwner(this.tree.getScene().getWindow());
        // stage.initModality(Modality.APPLICATION_MODAL);

        stage.showAndWait();
        Results_trasactions.clear();
        printtransactiontable();

    }

    @FXML
    public void ModifyClient() throws IOException {

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Modify_clients.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Modify Clients");
        stage.showAndWait();
        printclienttable();

    }

    @FXML
    public void DeleteClients() throws IOException {

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Delete_clients.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Delete Clients");
        stage.showAndWait();
        printclienttable();

    }

    @FXML
    public void DeleteTransactions() throws IOException {

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Delete_transactions.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Delete Transactions");
        stage.showAndWait();
        printclienttable();

    }

    @FXML
    public void ModifyTransaction() throws IOException {

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Modify_Transaction.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Modify Tranaction");
        stage.showAndWait();
        Results_trasactions.clear();
        printtransactiontable();

    }

    @FXML
    public void Addclient() throws IOException {

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Add_clients.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Add client");

        stage.showAndWait();
        Result_Client.clear();
        printclienttable();

    }

    @FXML
    public void displayWindow(String resource, String title) throws IOException {
        Stage stage = new Stage();
        Parent root;
        root = FXMLLoader.load(getClass().getClassLoader().getResource(resource));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle(title);

        stage.showAndWait();
    }

    public void loadCVS() throws IOException, Exception {

//Vector data = new Vector<>();
        readExcel();
        //excelreader.printCellDataToConsole(data);
        //Results_trasactions.clear();
        // printtransactiontable();

    }

    public void add_total() throws SQLException {
        try {
            ConnectionClass Databaseloader = new ConnectionClass();
            Databaseloader.getConnection();
            String sql = "SELECT SUM(Amount) AS total FROM Transactions;";
            ResultSet rs = Databaseloader.executeSQLRequestCommand(sql);
            Results_trasactions.setText("");
            while (rs.next()) {
                rs.getInt("total");
                Results_trasactions.setText("The total profits for today are: " + rs.getInt("total"));
            }
        } catch (SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    public void totalclientspending() throws SQLException {
        try {
            ConnectionClass Databaseloader = new ConnectionClass();
            Databaseloader.getConnection();
            String sql = "SELECT SUM(Amount) AS total FROM Transactions;";
            ResultSet rs = Databaseloader.executeSQLRequestCommand(sql);
            Results_trasactions.setText("");
            while (rs.next()) {
                double total = rs.getInt("total") / .7;
                Results_trasactions.setText("The total client spending today is: \n" + total);
            }
        } catch (SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    public void readExcel() throws FileNotFoundException, IOException, InvalidFormatException, Exception {
        Stage stage = new Stage();
        int Transaction_ID = 0;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel Files", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        file = fileChooser.showOpenDialog(stage);
        System.out.println(file);
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        //ByteArrayOutputStream out = QRCode.from(kk).to(ImageType.JPG).stream(); 
        List sheetData = new ArrayList();

        try {
            if (file.exists()) {
                System.out.println("File exists");
            } else {
                file.createNewFile(); // if the file does not exist, create it
                System.out.println("Created non-existing file");
            }
            if (file.canWrite()) {
                System.out.println("File can be written to");
            }

            FileOutputStream fos = new FileOutputStream(file);

            fos.flush();

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();
            ArrayList<String> client_description = new ArrayList<>();
            ArrayList<String> client_name = new ArrayList<>();
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                //For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                while (cellIterator.hasNext()) {
                    cell = cellIterator.next();
                    //This will change all Cell Types to String
                    //  cell.setCellType(CellType.STRING); 

                    switch (cell.getColumnIndex()) {
                        case 0:
                            date = cell.getDateCellValue();

                            break;
                        case 1:
                            System.out.print(
                                    cell.getStringCellValue() + " \t\t");

                            break;
                        case 2:
                            System.out.print(
                                    cell.getNumericCellValue() + " \t\t");

                            //date= cell.getDateCellValue();
                            description = row.getCell(1).getStringCellValue();
                            client_description.add(description);
                            break;

                    }
                }

                if (description.contains("from")) {
                    String[] new_str1 = description.split("from");

                    client_name.add(new_str1[1]);

                    amount = row.getCell(2).getNumericCellValue();

                    // dateFormat.parse(theDate.toString());
                    insertintoDatabase(dateFormat.format(date), amount, new_str1[0], new_str1[1], Transaction_ID);
                    Transaction_ID++;
                }
                // Display the two strings for comparison.

                if (description.contains("by")) {
                    String[] new_str1 = description.split("by");

                    description = row.getCell(1).getStringCellValue();
                    //amount =row.getCell(2).getNumericCellValue();
                    client_name.add(new_str1[1]);

                    amount = row.getCell(2).getNumericCellValue();

//     dateFormat.parse(theDate.toString());
                    insertintoDatabase(dateFormat.format(date), amount, new_str1[0], new_str1[1], Transaction_ID);
                    Transaction_ID++;
                }
            }

            ArrayList<String> newList = removeDuplicates(client_name);

            for (int i = 0; i < newList.size(); i++) {
                insertintoclient(i, newList.get(i));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        printtransactiontable();
        printclienttable();
    }

    public static ArrayList<String> removeDuplicates(ArrayList<String> list) {

        // Create a new ArrayList 
        ArrayList<String> newList = new ArrayList<String>();

        // Traverse through the first list 
        for (String element : list) {

            // If this element is not present in newList 
            // then add it 
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list 
        return newList;
    }

    public void insertintoclient(int Client_ID, String Client_name) {

        ConnectionClass connection = new ConnectionClass();
        connection.getConnection();

        String sql = "INSERT into Clients(Client_name_ID,Client_name) values('" + Client_ID + "', '" + Client_name + "');";
        connection.executeSQLCommand(sql);

    }

    public void insertintoDatabase(String Date, double Amount, String Desription, String Clientname, int TransactionID) throws Exception {
        ConnectionClass connection = new ConnectionClass();
        connection.getConnection();

        String sql = "INSERT into Transactions(Date,Description,Amount,Clientname,TransactionID) values('" + Date + "', '" + Desription + "', '" + Amount + "','" + Clientname + "','" + TransactionID + "');";
        connection.executeSQLCommand(sql);

    }
}

    
