package view.gui;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.entities.TextTab;
import model.utils.Constants;
import model.utils.FileHandler;

public class NotepadViewController implements Initializable, Constants {
	@FXML private Stage stage;

	@FXML private TabPane tabPane;
	@FXML private TextTab emptyTab;
	@FXML private SingleSelectionModel<Tab> selectionModel;

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
			for (File file : files) createTabs(file);
	}

	@FXML public void handleSaveAsAction() {
		File file = fileSaver.showSaveDialog(stage);
		TextTab currentTab = (TextTab) selectionModel.getSelectedItem();
		if (file != null && currentTab != null) {
			fileSaver.setInitialDirectory(file.getParentFile());
			String text = currentTab.getTextArea().getText().replaceAll("\n", System.getProperty("line.separator"));
			FileHandler.fileWriter(file.getAbsolutePath(),text);
			currentTab.setText(file.getName());
		}
	}

	@FXML public void handleNewTabs() {
		createTabs(null);
	}

	private void createTabs(File file) {
		emptyTab = new TextTab((file != null) ? file.getName():"untitled", (file != null) ? FileHandler.fileReader(file.getAbsolutePath()):"");
		tabPane.getTabs().add(emptyTab);
		selectionModel.selectLast();
	}

	@FXML public void handleCloseTabs() {
		Tab closeTab = selectionModel.getSelectedItem();
		if (closeTab != null)
			tabPane.getTabs().remove(closeTab);
	}

	@FXML public void closeApplication() {
		System.exit(0);
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
