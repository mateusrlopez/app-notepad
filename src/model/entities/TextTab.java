package model.entities;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;

public class TextTab extends Tab {
	@FXML private TextArea textArea;

	public TextTab(String title,String text) {
		super(title);
		textArea = new TextArea();
		textArea.setPrefSize(640,276);
		textArea.setText(text);
		setContent(textArea);
	}
}
