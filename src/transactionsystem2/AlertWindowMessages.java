/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionsystem2;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author icm2002
 */
public class AlertWindowMessages {

    private static final AlertWindowMessages INSTANCE = new AlertWindowMessages();

    public static AlertWindowMessages getInstance() {
        return INSTANCE;
    }

    public void confirmAdditionMessage(String objectAdded) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(objectAdded + " Added");
        alert.setHeaderText("Addition was successful.");
        String text = "The " + objectAdded.toLowerCase() + " has been successfully added.";
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void errorAddingMessage(String objectNotAdded, String result) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Adding The " + objectNotAdded);
        alert.setHeaderText("Addition error.");
        String text = "Adding the " + objectNotAdded.toLowerCase() + " failed due to: \n\n" + result;
        alert.setContentText(text);
        alert.showAndWait();

    }

    public void confirmModifyMessage(String objectModified) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(objectModified + " Modified");
        alert.setHeaderText("Modification was successful.");
        String text = "The " + objectModified.toLowerCase() + " has been successfully modified.";
        alert.setContentText(text);
        alert.showAndWait();

    }

    public void errorModifyingMessage(String objectNotModified, String result) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Modify The " + objectNotModified);
        alert.setHeaderText("Modification error.");
        String text = "Modifying the " + objectNotModified.toLowerCase() + " failed due to: \n\n" + result;
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void confirmDeletionMessage(String objectDeleted) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(objectDeleted + " Deleted");
        alert.setHeaderText("Deletion successful.");
        String text = "The " + objectDeleted.toLowerCase() + " has been successfully deleted.";
        alert.setContentText(text);
        alert.showAndWait();

    }

    public void errorDeletingMessage(String objectNotDeleted, String result) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Deleting The " + objectNotDeleted);
        alert.setHeaderText("Deletion error.");
        String text = "Deleting the " + objectNotDeleted.toLowerCase() + " failed due to: \n\n" + result;
        alert.setContentText(text);
        alert.showAndWait();
    }

    public ButtonType changingObjectsMessage(String objectChanged) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.getButtonTypes().add(ButtonType.CANCEL);
        alert.setTitle("Changes not saved.");
        String text = "A new " + objectChanged.toLowerCase() + " code has been entered. Are you sure you want to switch? "
                + "Any changes you made to the previous " + objectChanged.toLowerCase() + " will not be saved.";

        alert.setContentText(text);
        alert.showAndWait();

        return alert.getResult();
    }

    //should not be seen if the program is coded properly.
    public void noObjectFoundMessage(String objectMissing) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("No " + objectMissing + " Found");
        alert.setHeaderText("Entry was not found.");
        String text = "No " + objectMissing.toLowerCase() + " was found with that code.";
        alert.setContentText(text);
        alert.showAndWait();

    }

    public void noObjectSelectedMessage(String objectNotSelected) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("No " + objectNotSelected + " Selected");
        alert.setHeaderText("Entry not selected");
        String text = "Please select the " + objectNotSelected.toLowerCase() + " code and try again.";
        alert.setContentText(text);
        alert.showAndWait();

    }
}
