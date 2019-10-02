package model.entities;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class TextTab extends Tab {
	@FXML private AnchorPane anchorPane;
	@FXML public TextArea textArea;
	
	public TextTab(String title) {
		super(title);
		anchorPane = new AnchorPane();
		anchorPane.setPrefSize(640,276);
		TextArea textArea = new TextArea();
		AnchorPane.setTopAnchor(textArea,0.0);
		AnchorPane.setBottomAnchor(textArea,0.0);
		AnchorPane.setLeftAnchor(textArea,0.0);
		AnchorPane.setRightAnchor(textArea,0.0);
		anchorPane.getChildren().add(textArea);
		setContent(anchorPane);
	}
}
