package view.gui.utils;

import javafx.scene.control.TextField;

public class Constraints {
	public static void setTextFieldDouble(TextField textField) {
		textField.textProperty().addListener((obs,oldValue,newValue) -> {
			if(newValue != null && !newValue.matches("\\d*([\\.]\\d*)?"))
				textField.setText(oldValue);
		});
	}
	
	public static void setTextFieldNonNumeric(TextField textField) {
		textField.textProperty().addListener((obs,oldValue,newValue) -> {
			if(newValue != null && !(newValue.matches("\\w*") && newValue.matches("\\D*")))
				textField.setText(oldValue);
		});
	}
}
