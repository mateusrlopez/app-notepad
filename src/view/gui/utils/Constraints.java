package view.gui.utils;

import javafx.scene.control.TextField;

public class Constraints {
	public static void setTextFieldInteger(TextField textField) {
		textField.textProperty().addListener((obs,oldValue,newValue) -> {
			if(newValue != null && !newValue.matches("\\d*"))
				textField.setText(oldValue);
		});
	}
}
