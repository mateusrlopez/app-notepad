package view.gui.utils.dialogs;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.gui.NotepadViewController;

public class LocateDialogController implements Initializable {
	@FXML 
	private TextField toLocateTxtField;
	
	@FXML 
	private Button toLocateBt;
	
	public static NotepadViewController mainController;
	
	public static SimpleStringProperty toLocateText = new SimpleStringProperty("");
	
	@Override 
	public void initialize(URL url, ResourceBundle rb) {
		toLocateTxtField.setText(toLocateText.get());
		toLocateTxtField.textProperty().addListener((obs,oldValue,newValue) -> {
			toLocateBt.setDisable(newValue != null && newValue.equals(""));
		});
	}
	
	@FXML
	private void onLocateBtAction() {
		toLocateText.set(toLocateTxtField.getText());
		mainController.locateFunction();
	}
	
	@FXML private void cancelAction(ActionEvent event) {
		Button button = (Button)event.getSource();
		((Stage)button.getScene().getWindow()).close();
	}
	
}
