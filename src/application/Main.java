package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.gui.NotepadViewController;

public class Main extends Application {
	@Override 
	public void start(Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gui/Notepad.fxml"));
			Parent parent = (Parent) loader.load();
			Scene scene = new Scene(parent);
			NotepadViewController controller = (NotepadViewController) loader.getController();
			stage.setScene(scene);
			controller.setStage(stage);
			stage.setTitle("Notepad");
			stage.getIcons().add(new Image("/view/images/Notepad.png"));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
