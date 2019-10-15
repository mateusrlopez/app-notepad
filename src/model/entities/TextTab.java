package model.entities;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import model.utils.Constants;
import model.utils.FileHandler;
import view.gui.NotepadViewController;
import view.gui.TabViewController;
import view.gui.utils.Dialogs;

public class TextTab extends Tab implements Constants {
	@FXML private TextArea textArea;
	
	private String filePath;
	private boolean firstTimeSave;
	private boolean saved;
	
	private TabViewController tabController;

	public TextTab(File file,NotepadViewController controller,Font font) {
		super((file == null) ? "untitled" : file.getName());
		
		firstTimeSave = file == null;
		saved = true;
		
		this.setOnCloseRequest(event -> {
			if(!saved) {
				Optional<ButtonType> result = Dialogs.showSaveAlert(BACKSP.apply(getText()),event);
				if(!result.isEmpty()) {
					if(result.get().equals(Dialogs.buttonSalvar)) controller.saveAction();
					else if(result.get().equals(Dialogs.buttonCancelar)) event.consume();
				}
			}
		});
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/gui/Tab.fxml"));
		try {
			Parent parent = (Parent) loader.load();
			tabController = loader.<TabViewController>getController();
			textArea = tabController.getTextArea();
			if(font != null) textArea.setFont(font);
			textArea.setText((file != null) ? FileHandler.fileReader(file.getAbsolutePath()):"");
			textArea.textProperty().addListener((obs, oldValue, newValue) -> {
				if(newValue != null && saved) {
					setText(getText()+"*");
					saved = false;
				}
			});
			setContent(parent);
		} catch (IOException e) {
			e.printStackTrace();
		}

		filePath = (file == null) ? null : file.getAbsolutePath();
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
