package view.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class TabViewController implements Initializable {
	@FXML private TextArea textArea;
	
	@FXML private Label labelColumnsLines;
	
	private int line;
	private int column;

	@Override public void initialize(URL url, ResourceBundle rb) {
		labelColumnsLines.setText("Line 1, Column 1");
		
		textArea.hoverProperty().addListener(event -> {
			if(textArea.isHover()) textArea.requestFocus();
		});
		
		textArea.caretPositionProperty().addListener(event -> {
			calculateLinesColumns();
			labelColumnsLines.setText(String.format("Line %d, Column %d",line,column));
		});
	}
	
	public TextArea getTextArea() {
		return textArea;
	}
	
	private void calculateLinesColumns() {
		String beforeText = textArea.getText().substring(0,textArea.getCaretPosition());
		String currentLine = beforeText.substring((beforeText.lastIndexOf("\n") == -1) ? 0:beforeText.lastIndexOf("\n"));
		int lastIndex = 0;
		line = 1;
		
		column = currentLine.length() + ((currentLine.matches("[\\n](\\w*)?")) ? 0:1);
		while(lastIndex != -1) {
		    lastIndex = beforeText.indexOf("\n",lastIndex);
		    if(lastIndex != -1) {
		    	line++;
			    lastIndex++;
		    }
		}
	}
}
