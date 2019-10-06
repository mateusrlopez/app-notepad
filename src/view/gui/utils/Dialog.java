package view.gui.utils;

import java.util.Optional;

import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.gui.NotepadViewController;

public class Dialog {
	public static void showDialog(String title, Event event) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Notepad");
		alert.setContentText(String.format("Deseja salvar as alterações em %s?",title));
		alert.setHeaderText(null);
		
		ButtonType buttonSalvar = new ButtonType("Salvar",ButtonData.RIGHT);
		ButtonType buttonDontSave = new ButtonType("Não Salvar",ButtonData.RIGHT);
		ButtonType buttonCancelar = new ButtonType("Cancelar",ButtonData.RIGHT);
		
		alert.getButtonTypes().clear();
		alert.getButtonTypes().addAll(buttonSalvar,buttonDontSave,buttonCancelar);
		
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/view/images/Notepad.png"));
		
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == buttonSalvar) NotepadViewController.saveAction();
		else if(result.get() == buttonCancelar) event.consume();	
	}
}
