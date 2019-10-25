package view.gui.utils.dialogs;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.gui.NotepadViewController;

public class ReplaceDialogController implements Initializable {
	public static NotepadViewController controller;
	
	@FXML private TextField locateTextField;
	@FXML private TextField replaceTextField;
	
	@FXML private Button btSubstituir;
	@FXML private Button btSubstituirTodos;

	@Override public void initialize(URL url, ResourceBundle rb) {
		locateTextField.textProperty().addListener((obs,oldValue,newValue) -> {
				btSubstituir.setDisable((newValue != null && newValue.equals("")));
				btSubstituirTodos.setDisable((newValue != null && newValue.equals("")));
		});
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
