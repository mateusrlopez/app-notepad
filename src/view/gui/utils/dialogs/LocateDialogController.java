package view.gui.utils.dialogs;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LocateDialogController implements Initializable {
	@FXML private TextField toLocateTxtField;
	
	public static String toLocateText;
	
	@Override public void initialize(URL url, ResourceBundle rb) {
		
	}
	
	@FXML private void cancelAction(ActionEvent event) {
		Button button = (Button)event.getSource();
		((Stage)button.getScene().getWindow()).close();
	}
	
}
