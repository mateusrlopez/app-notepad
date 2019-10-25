package view.gui.utils;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.utils.Constants;

public class Constraints {
	public static void setTextFieldInteger(TextField textField) {
		textField.textProperty().addListener((obs,oldValue,newValue) -> {
			if(newValue != null && !newValue.matches("\\d*"))
				textField.setText(oldValue);
		});
	}
}
