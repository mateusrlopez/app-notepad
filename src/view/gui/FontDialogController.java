package view.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.utils.Constants;
import view.gui.utils.Constraints;

public class FontDialogController implements Initializable,Constants {
	@FXML private TextField fontTextField;
	@FXML private TextField sizeTextField;
	
	@FXML private ListView<String> fontListView;
	@FXML private ListView<Integer> sizeListView;
	
	@FXML private CheckBox boldCheckBox;
	@FXML private CheckBox italicCheckBox;
	
	@FXML private Label labelPreview;
	
	@FXML private Button btOk;
	@FXML private Button btCancelar;
	
	protected static Font font;	
	
	private static String lastFont;
	private static double lastSize;
	
	private ObservableList<String> fonts = FXCollections.observableArrayList(Font.getFamilies());
	private ObservableList<Integer> sizes = FXCollections.observableArrayList(SIZES_VALUES);
	
	@Override public void initialize(URL url, ResourceBundle rb) {
		fontTextField.setText((lastFont != null)? lastFont:"Arial");
		sizeTextField.setText((lastSize != 0.0) ? String.format("%f",lastSize):"12");
		
		fontListView.setItems(fonts);
		sizeListView.setItems(sizes);
		
		Constraints.setTextFieldDouble(sizeTextField);
		Constraints.setTextFieldNonNumeric(fontTextField);
	}
	
	@FXML private void buttonAction(ActionEvent event) {
		Button button = (Button)event.getSource();
		if(button.getId().equals("btOk")) {
			font = Font.font(fontTextField.getText(), (boldCheckBox.isSelected())? FontWeight.BOLD:null,
			(italicCheckBox.isSelected())? FontPosture.ITALIC:FontPosture.REGULAR,Double.parseDouble(sizeTextField.getText()));
			lastSize = Double.parseDouble(sizeTextField.getText());
			lastFont = fontTextField.getText();
		} else 
			font = null;
		((Stage)button.getScene().getWindow()).close();
	}

}
