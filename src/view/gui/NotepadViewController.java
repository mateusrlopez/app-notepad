package view.gui;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.entities.FileMenuItem;
import model.entities.TextTab;
import model.utils.Constants;
import model.utils.FileHandler;
import view.gui.utils.Dialog;

public class NotepadViewController implements Initializable, Constants {
	@FXML private Stage stage;

	@FXML private TabPane tabPane;
	@FXML private TextTab emptyTab;
	@FXML private SingleSelectionModel<Tab> selectionModel;
	
	@FXML private Menu openRecent;
	
	@FXML private final Clipboard clipboard = Clipboard.getSystemClipboard();
    @FXML private final ClipboardContent content = new ClipboardContent();

    TextTab currentTab;
	FileChooser fileLoader = new FileChooser();
	FileChooser fileSaver = new FileChooser();

	@Override public void initialize(URL url, ResourceBundle rb) {
		fileLoader.setTitle("Abrir");
		fileLoader.setInitialDirectory(new File(INITIAL_DIRECTORY));
		fileLoader.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivo de texto", "*.txt"));

		fileSaver.setTitle("Salvar como");
		fileSaver.setInitialDirectory(new File(INITIAL_DIRECTORY));
		fileSaver.setInitialFileName("Texto");
		fileSaver.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivo de texto", "*.txt"));

		selectionModel = tabPane.getSelectionModel();
	}

	@FXML public void handleOpenAction() {
		List<File> files = fileLoader.showOpenMultipleDialog(stage);
		if (files != null)
			for (File file : files) {
				createTabs(file);
				handleAddOpenRecent(file.getAbsolutePath());
			}
	}
	
	private void handleAddOpenRecent(String filePath) {
		FileMenuItem menuItem = new FileMenuItem(filePath);
		if(!openRecent.getItems().contains(menuItem)) {
			menuItem.setOnAction(event -> createTabs(new File(menuItem.getText())));
			if(openRecent.getItems().size() == 7) openRecent.getItems().remove(0);
			openRecent.getItems().add(menuItem);
		}
	}

	@FXML public void handleSaveAsAction() {
		File file = fileSaver.showSaveDialog(stage);
		currentTab = (TextTab) selectionModel.getSelectedItem();
		if (file != null && currentTab != null) {
			handleSaves(true,file);
		}
	}
	
	@FXML public void handleSaveAction() {
		currentTab = (TextTab) selectionModel.getSelectedItem();
		if(currentTab != null && !currentTab.isSaved()) {
			if(currentTab.isFirstTimeSave()) {
				File file = fileSaver.showSaveDialog(stage);
				if(file != null) handleSaves(true,file);
			} else handleSaves(true,null);
		}
	}
	
	private void handleSaves(boolean type,File file) {
		String text = currentTab.getTextArea().getText().replaceAll("\n", System.getProperty("line.separator"));
		FileHandler.fileWriter((type)?file.getAbsolutePath():currentTab.getFilePath(),text);
		currentTab.setText((type)?file.getName():BACKSP.apply(currentTab.getText()));
		currentTab.setSaved(true);
		if(type) {
			fileSaver.setInitialDirectory(file.getParentFile());
			currentTab.setFirstTimeSave(false);
			currentTab.setFilePath(file.getAbsolutePath());
		}
	}

	@FXML public void handleNewTabs() {
		createTabs(null);
	}

	private void createTabs(File file) {
		emptyTab = new TextTab(file);
		tabPane.getTabs().add(emptyTab);
		selectionModel.selectLast();
	}

	@FXML public void handleCloseTabs() {
		currentTab = (TextTab) selectionModel.getSelectedItem();
		if (currentTab != null) tabPane.getTabs().remove(currentTab);
	}
	
	@FXML public void handleCut() {
		TextTab currentTab = (TextTab) selectionModel.getSelectedItem();
		if(currentTab != null) {
		    content.putString(currentTab.getTextArea().getSelectedText());
		    clipboard.setContent(content);
		    currentTab.getTextArea().replaceSelection("");
		}
	}
	
	@FXML public void handleCopy() {
		TextTab currentTab = (TextTab) selectionModel.getSelectedItem();
		if(currentTab != null) {
		    content.putString(currentTab.getTextArea().getSelectedText());
		    clipboard.setContent(content);
		}
	}
	
	@FXML public void handlePaste() {
		TextTab currentTab = (TextTab) selectionModel.getSelectedItem();
		if(currentTab != null && clipboard.hasString()) {
			TextArea textArea = currentTab.getTextArea();
			if(textArea.isFocused()) {
				if(!textArea.getSelectedText().equals("")) textArea.replaceSelection(clipboard.getString());
				else textArea.appendText(clipboard.getString());
			}
		}
	}
	
	@FXML public void handleSelectAll() {
		TextTab currentTab = (TextTab) selectionModel.getSelectedItem();
		if (currentTab != null) {
			TextArea textArea = currentTab.getTextArea();
			if(textArea.isFocused()) textArea.selectAll();
		}
	}
	
	@FXML public void handleUnselectAll() {
		TextTab currentTab = (TextTab) selectionModel.getSelectedItem();
		if (currentTab != null) {
			TextArea textArea = currentTab.getTextArea();
			if(textArea.isFocused() && !textArea.getSelectedText().equals("")) textArea.deselect();
		}
	}

	@FXML public void closeApplication() {
		System.exit(0);
	}

	public void setStage(Stage stage) {
		this.stage = stage;
		
		stage.setOnCloseRequest(event -> {
			for(int i=0; i<tabPane.getTabs().size(); i++) {
				currentTab = (TextTab) tabPane.getTabs().get(i);
				if(!currentTab.isSaved()) Dialog.showDialog(BACKSP.apply(currentTab.getText()),event);
			}
		});
	}
}
