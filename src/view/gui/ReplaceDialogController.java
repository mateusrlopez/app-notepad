package view.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ReplaceDialogController implements Initializable {
	public static NotepadViewController controller;
	
	@FXML private TextField locateTextField;
	@FXML private TextField replaceTextField;

	@Override public void initialize(URL url, ResourceBundle rb) {
		
	}
	
	@FXML private void onReplaceAction() {
		controller.replaceFunction(false,locateTextField.getText(),replaceTextField.getText());
	}
	
	@FXML private void onReplaceAllAction() {
		controller.replaceFunction(true,locateTextField.getText(),replaceTextField.getText());
	}
	
	@FXML private void onCancelAction(ActionEvent event) {
		Button button = (Button)event.getSource();
		((Stage)button.getScene().getWindow()).close();
	}
	
}
