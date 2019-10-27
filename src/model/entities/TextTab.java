package model.entities;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import model.utils.Constants;
import model.utils.FileHandler;
import view.gui.NotepadViewController;
import view.gui.TabViewController;
import view.gui.utils.dialogs.Dialogs;
import view.gui.utils.dialogs.FontDialogController;

public class TextTab extends Tab implements Constants {
	@FXML private TextArea textArea;
	
	private String filePath;
	private boolean firstTimeSave;
	private boolean saved;
	public static SimpleBooleanProperty wrapTextProperty = new SimpleBooleanProperty();
	
	private TabViewController tabController;

	public TextTab(File file,NotepadViewController controller) {
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
			ScrollPane sPane = loader.load();			
			tabController = loader.<TabViewController>getController();
			textArea = tabController.getTextArea();
			textArea.fontProperty().bind(FontDialogController.fontProperty);		
			textArea.setText((file != null) ? FileHandler.fileReader(file.getAbsolutePath()):"");
			textArea.textProperty().addListener((obs, oldValue, newValue) -> {				
				if(newValue != null && saved) {
					setText(getText()+"*");
					saved = false;
				}
			});
			setContent(sPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		textArea.wrapTextProperty().bind(wrapTextProperty);
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
