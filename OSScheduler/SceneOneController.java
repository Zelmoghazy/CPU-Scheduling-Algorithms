package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SceneOneController {
	@FXML
	Label label, quantumLabel;
	@FXML
	RadioButton FCFS,SJFP,SJFNP,RR,PP,PNP;
	
	@FXML
	TextField quantum;
	
	private Parent root;
	private Scene scene;
	private Stage stage;
	
	public void roundRobin(ActionEvent event) {
		if(RR.isSelected()) {
			quantum.setDisable(false);
			quantumLabel.setDisable(false);
			
			quantum.setVisible(true);
			quantumLabel.setVisible(true);
		
		}
		else {
			quantum.setDisable(true);
			quantumLabel.setDisable(true);
			quantum.setVisible(false);
			quantumLabel.setVisible(false);

		}
	}
	
	public void selectAlgo(ActionEvent event) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ProcessTable.fxml"));
		root = loader.load();
		ProcessTableController secondController = loader.getController();
		AddProcessController.setController(secondController);
		
		if(FCFS.isSelected()) {
			secondController.setLabel(FCFS.getText());
		}
			
		else if(SJFP.isSelected())
			secondController.setLabel(SJFP.getText());
		else if(SJFNP.isSelected())
			secondController.setLabel(SJFNP.getText());
		else if(RR.isSelected()) {
			if(quantum.getText().isEmpty()) {
				errorDialog("Quantum time has to be determined");
				return;
			}
			secondController.setLabel(RR.getText());
			try {
				secondController.setQuantum(Integer.parseInt(quantum.getText()));
			} catch (Exception e) {
				errorDialog("Quantum time cannot be text");
				return;
			}
			
		}
			
		else if(PP.isSelected())
			secondController.setLabel(PP.getText());
		else if(PNP.isSelected())
			secondController.setLabel(PNP.getText());
		else {
			errorDialog("NO Algorithm is selected");
			return;
		}
		scene = new Scene(root);
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	private void errorDialog(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }
    
	public void LogOut(Stage stage) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("are you sure you want to log out? ");
		if(alert.showAndWait().get() == ButtonType.OK) {
			stage.close();
		}
	}
	
}
