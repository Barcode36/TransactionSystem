/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionsystem2;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

/**
 *
 * @author dylan
 */
public class SimpleWriteExcelExample {
    /*
  public static void printCellDataToConsole() throws Exception {
        try {
            ConnectionClass Databaseloader = new ConnectionClass();
            Databaseloader.getConnection();

            String sql = "INSERT INTO Transaction\"\n" + "+ \"(Date, Description, Amount,Clientname,TransactionID) VALUES\"\n" + "(?,?,?,?,?)";

            PreparedStatement sql_statement = null;
            Databaseloader.executeSQLCommand(sql);
            FileInputStream input = new FileInputStream("‪‪C:\\Users\\dylan\\Desktop\\Tranactions.xlsx");
            POIFSFileSystem fs = new POIFSFileSystem(input);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            Iterator rows = sheet.rowIterator();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                //HSSFRow HSSFRow = sheet.getRow(i);
                HSSFRow row = sheet.getRow(i);
                Date date = (Date) row.getCell(0).getDateCellValue();
                String Description = row.getCell(1).getStringCellValue();
                int Amount = (int) row.getCell(2).getNumericCellValue();
                String Clientname = row.getCell(3).getStringCellValue();
                int TransactionID = (int) row.getCell(4).getNumericCellValue();

                sql = "INSERT INTO tablename VALUES('" + date + "','" + Description + "','" + Amount + "','" + Clientname + "',''" + TransactionID + "');";

                System.out.println("Import rows " + i);
            }
            while (rows.hasNext()) {
                HSSFRow row = (HSSFRow) rows.next();
                Iterator cells = row.cellIterator();
                while (cells.hasNext()) {
                    HSSFCell cell = (HSSFCell) cells.next();
                    switch (cell.getCellType()) {
                        case Czell.CELL_TYPE_STRING: //handle string columns
                            sql_statement.setString(1, cell.getStringCellValue());
                            break;
                        case Cell.CELL_TYPE_NUMERIC: //handle double data
                            sql_statement.setDouble(2, cell.getNumericCellValue());
                            break;
                    }
                }
            }
            Databaseloader.executeSQLCommand(sql);
            Databaseloader.closeConnection();

        } //  catch (ClassNotFoundException e){
        //    System.err.println(e.getMessage());
        //}
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ioe) {
            System.out.println(ioe);
        }

        System.out.println("Success import excel to mysql table");

    }
      }
*/

} 

