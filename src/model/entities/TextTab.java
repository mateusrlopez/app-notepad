package model.entities;

import java.io.File;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import model.utils.Constants;
import model.utils.FileHandler;
import view.gui.NotepadViewController;
import view.gui.utils.Dialogs;

public class TextTab extends Tab implements Constants {
	@FXML private TextArea textArea;
	private String filePath;
	private boolean firstTimeSave;
	private boolean saved;

	public TextTab(File file,NotepadViewController controller) {
		super((file == null) ? "untitled" : file.getName());
		firstTimeSave = file == null;
		saved = true;
		textArea = new TextArea();
		textArea.setPrefSize(640,276);
		textArea.setFocusTraversable(false);
		textArea.setText((file != null) ? FileHandler.fileReader(file.getAbsolutePath()):"");
		textArea.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null && saved) {
				setText(getText()+"*");
				saved = false;
			}
		});
		this.setOnCloseRequest(event -> {
			if(!saved) {
				Optional<ButtonType> result = Dialogs.showSaveAlert(BACKSP.apply(getText()));
				if(result.get().equals(Dialogs.buttonSalvar)) controller.saveAction();
				else if(result.get().equals(Dialogs.buttonCancelar)) event.consume();
			}
		});
		filePath = (file == null) ? null : file.getAbsolutePath();
		setContent(textArea);
	}
	
	public TextArea getTextArea() {
		return textArea;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public boolean isFirstTimeSave() {
		return firstTimeSave;
	}

	public void setFirstTimeSave(boolean firstTimeSave) {
		this.firstTimeSave = firstTimeSave;
	}

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}
}
