package view.gui.utils.dialogs;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.utils.Constants;
import view.gui.utils.Constraints;

public class FontDialogController implements Initializable,Constants {
	@FXML
	private TextField fontTextField;
	@FXML 
	private TextField sizeTextField;
	
	@FXML 
	private ListView<String> fontListView;
	@FXML 
	private ListView<Integer> sizeListView;
	
	@FXML 
	private CheckBox boldCheckBox;
	@FXML
	private CheckBox italicCheckBox;
	
	@FXML
	private Label labelPreview;
	
	@FXML 
	private Button btOk;
	@FXML 
	private Button btCancelar;
	
	public static SimpleObjectProperty<Font> fontProperty = new SimpleObjectProperty<Font>(Font.font("Arial",12));
	
	private static String lastFont;
	private static double lastSize;
	
	private ObservableList<String> fonts = FXCollections.observableArrayList(Font.getFamilies());
	private ObservableList<Integer> sizes = FXCollections.observableArrayList(SizeValues);
	
	@Override 
	public void initialize(URL url, ResourceBundle rb) {
		fontTextField.setText((lastFont != null)? lastFont:"Arial");
		sizeTextField.setText((lastSize != 0.0) ? String.format("%d",(int)lastSize):"12");
		
		this.addListener();
		
		fontListView.setItems(fonts);
		sizeListView.setItems(sizes);
		
		Constraints.setTextFieldInteger(sizeTextField);
	}
	
	@FXML 
	private void buttonAction(ActionEvent event) {
		Button button = (Button)event.getSource();
		if(button.getId().equals("btOk")) {
			fontProperty.set(Font.font(fontTextField.getText(), (boldCheckBox.isSelected())? FontWeight.BOLD:null,
			(italicCheckBox.isSelected())? FontPosture.ITALIC:FontPosture.REGULAR,Double.parseDouble(sizeTextField.getText())));
			lastSize = Double.parseDouble(sizeTextField.getText());
			lastFont = fontTextField.getText();
		}

		((Stage)button.getScene().getWindow()).close();
	}
	
	@FXML 
	private void listViewAction(MouseEvent event) {
		ListView listView = (ListView) event.getSource();
		TextField textField = (listView.getId().equals("fontListView"))? fontTextField:sizeTextField;
		textField.setText(listView.getSelectionModel().getSelectedItem().toString());
	}
	
	private void addListener() {
		fontTextField.textProperty().addListener((obs,oldValue,newValue) -> {
			if(Font.getFamilies().contains(newValue))
				labelPreview.setFont(Font.font(newValue));
		});
		
		sizeTextField.textProperty().addListener((obs,oldValue,newValue) -> {
			if(newValue != null)
				labelPreview.setFont(Font.font((!newValue.equals(""))?Double.parseDouble(newValue):12));
		});
		
		boldCheckBox.selectedProperty().addListener(event -> {
			labelPreview.setFont(Font.font("",(boldCheckBox.isSelected())?FontWeight.BOLD:FontWeight.NORMAL,
					(italicCheckBox.isSelected())?FontPosture.ITALIC:FontPosture.REGULAR,
					(!sizeTextField.getText().equals(""))? Double.parseDouble(sizeTextField.getText()):12));
		});
		
		italicCheckBox.selectedProperty().addListener(event -> {
			labelPreview.setFont(Font.font("",(boldCheckBox.isSelected())?FontWeight.BOLD:FontWeight.NORMAL,
					(italicCheckBox.isSelected())?FontPosture.ITALIC:FontPosture.REGULAR, 
					(!sizeTextField.getText().equals(""))? Double.parseDouble(sizeTextField.getText()):12));
		});
	}

}
