package view.gui;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.FileMenuItem;
import model.entities.TextTab;
import model.utils.Constants;
import model.utils.FileHandler;
import view.gui.utils.dialogs.Dialogs;
import view.gui.utils.dialogs.FontDialogController;
import view.gui.utils.dialogs.ReplaceDialogController;

public class NotepadViewController implements Initializable, Constants {
	@FXML private Stage stage;

	@FXML private TabPane tabPane;
	@FXML private TextTab emptyTab;
	@FXML private SingleSelectionModel<Tab> selectionModel;
	
	@FXML private Menu openRecent;
	@FXML private CheckMenuItem wrapText;
	
	@FXML private final Clipboard clipboard = Clipboard.getSystemClipboard();
    @FXML private final ClipboardContent content = new ClipboardContent();

    private TextTab currentTab;
    private TextArea textArea;
	private FileChooser fileLoader = new FileChooser();
	private FileChooser fileSaver = new FileChooser();

	@Override public void initialize(URL url, ResourceBundle rb) {
		fileLoader.setTitle("Abrir");
		settingFileChooser(fileLoader);

		fileSaver.setTitle("Salvar como");
		fileSaver.setInitialFileName("Texto");
		settingFileChooser(fileSaver);
		
		TextTab.wrapTextProperty.bind(wrapText.selectedProperty());

		selectionModel = tabPane.getSelectionModel();
	}
	
	private void settingFileChooser(FileChooser fileChooser) {
		fileChooser.setInitialDirectory(new File(INITIAL_DIRECTORY));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Arquivo de texto", "*.txt"),
				new FileChooser.ExtensionFilter("Arquivo C/C++", "*.c", "*.cpp"),
				new FileChooser.ExtensionFilter("Arquivo Java", "*.java"),
				new FileChooser.ExtensionFilter("Arquivo Python", "*.py"));
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

	@FXML private void handleSaveAsAction() {
		File file = fileSaver.showSaveDialog(stage);
		currentTab = (TextTab) selectionModel.getSelectedItem();
		if (file != null && currentTab != null) 
			handleSaves(true,file);			
		}
	
	@FXML private void handleSaveAction() {
		saveAction();
	}
	
	public void saveAction() {
		currentTab = (TextTab) selectionModel.getSelectedItem();
		if(currentTab != null) {
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
			handleAddOpenRecent(file.getAbsolutePath());
		}
	}

	@FXML private void handleNewTabs() {
		createTabs(null);
	}

	private void createTabs(File file) {
		emptyTab = new TextTab(file,this);
		tabPane.getTabs().add(emptyTab);
		selectionModel.selectLast();
	}
	
	@FXML private void handleUndo() {
		currentTab = (TextTab) selectionModel.getSelectedItem();
		if(currentTab != null) {
			textArea = currentTab.getTextArea();
			if(textArea.isUndoable()) textArea.undo();
		}
	}
	
	@FXML private void handleCut() {
		TextTab currentTab = (TextTab) selectionModel.getSelectedItem();
		if(currentTab != null) {
		    content.putString(currentTab.getTextArea().getSelectedText());
		    clipboard.setContent(content);
		    currentTab.getTextArea().replaceSelection("");
		}
	}
	
	@FXML private void handleCopy() {
		TextTab currentTab = (TextTab) selectionModel.getSelectedItem();
		if(currentTab != null) {
		    content.putString(currentTab.getTextArea().getSelectedText());
		    clipboard.setContent(content);
		}
	}
	
	@FXML private void handlePaste() {
		TextTab currentTab = (TextTab) selectionModel.getSelectedItem();
		if(currentTab != null && clipboard.hasString()) {
			textArea = currentTab.getTextArea();
			if(textArea.isFocused()) {
				if(!textArea.getSelectedText().equals("")) textArea.replaceSelection(clipboard.getString());
				else textArea.appendText(clipboard.getString());
			}
		}
	}
	
	@FXML private void handleDelete() {
		currentTab = (TextTab) selectionModel.getSelectedItem();
		if (currentTab != null) {
			textArea = currentTab.getTextArea();
			if(!textArea.getSelectedText().equals("")) textArea.replaceSelection("");
			else textArea.deletePreviousChar();
		}
	}
	
	@FXML private void handleSelectAll() {
		currentTab = (TextTab) selectionModel.getSelectedItem();
		if (currentTab != null) {
			textArea = currentTab.getTextArea();
			if(textArea.isFocused()) textArea.selectAll();
		}
	}
	
	@FXML private void addDate() {
		currentTab = (TextTab) selectionModel.getSelectedItem();
		if(currentTab != null) {
			textArea = currentTab.getTextArea();
			if (textArea.getSelectedText().equals("")) textArea.appendText(SDF.format(new Date()));
			else textArea.replaceSelection(SDF.format(new Date()));;
		}
	}
	
	@FXML private void changeFont() {
		createFontDialog();
	}
	
	private void createFontDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/utils/dialogs/FontDialog.fxml"));
			Pane pane = loader.load();
			
			Stage stage = new Stage();
			stage.setTitle("Escolha uma fonte");
			stage.getIcons().add(new Image("/view/images/Notepad.png"));
			stage.setScene(new Scene(pane));
			stage.setResizable(false);
			stage.setOnCloseRequest(event -> {
				FontDialogController.defaultFontProperty.set(FontDialogController.fontProperty.get());
				FontDialogController.fontProperty.set(null);
			});
			stage.initOwner(this.stage);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.showAndWait();			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML private void createReplaceDialog() {
		currentTab = (TextTab) selectionModel.getSelectedItem();
		if(currentTab != null) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/utils/dialogs/ReplaceDialog.fxml"));
				ReplaceDialogController.controller = this;
				Pane pane = loader.load();
				
				Stage stage = new Stage();
				stage.setTitle("Substituir");
				stage.getIcons().add(new Image("/view/images/Notepad.png"));
				stage.setScene(new Scene(pane));
				stage.setResizable(false);
				stage.initOwner(this.stage);
				stage.initModality(Modality.WINDOW_MODAL);
				stage.showAndWait();			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void replaceFunction(boolean all, String locate, String replace) {
		currentTab = (TextTab) selectionModel.getSelectedItem();
		TextArea textArea = currentTab.getTextArea();
		if(all) {
			String replaceText = textArea.getText().replaceAll(locate,replace);
			textArea.setText(replaceText);
		} else {
			if(!textArea.getText().contains(locate)) {
				Toolkit.getDefaultToolkit().beep();
				Dialogs.showReplaceAlert(locate);
			} else {
				String replaceText = textArea.getText().replaceFirst(locate, replace);
				textArea.setText(replaceText);
			}
		}
	}
	
	@FXML private void createLocateDialog() {
		currentTab = (TextTab) selectionModel.getSelectedItem();
		if(currentTab != null) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/utils/dialogs/LocateDialog.fxml"));
				ReplaceDialogController.controller = this;
				Pane pane = loader.load();
				
				Stage stage = new Stage();
				stage.setTitle("Localizar");
				stage.getIcons().add(new Image("/view/images/Notepad.png"));
				stage.setScene(new Scene(pane));
				stage.setResizable(false);
				stage.initOwner(this.stage);
				stage.initModality(Modality.WINDOW_MODAL);
				stage.showAndWait();			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void locateFunction() {
		currentTab = (TextTab) selectionModel.getSelectedItem();
		if(currentTab != null) {
			
		}
	}

	@FXML private void closeApplication() {
		System.exit(0);
	}

	public void setStage(Stage stage) {
		this.stage = stage;
		
		stage.setOnCloseRequest(event -> {
			for(int i=0; i<tabPane.getTabs().size(); i++) {
				currentTab = (TextTab) tabPane.getTabs().get(i);
				if(!currentTab.isSaved()) { 
					Optional<ButtonType> result = Dialogs.showSaveAlert(BACKSP.apply(currentTab.getText()),event);
					if(!result.isEmpty()) {
						if(result.get().equals(Dialogs.buttonSalvar)) saveAction();
						else if(result.get().equals(Dialogs.buttonCancelar)) event.consume();
					}
				}
			}
		});
	}
}
