//
package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.function.UnaryOperator;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.stage.Stage;

//controller to control creating processes scene
public class AddProcessController {
    // a static object to add processes to the same object every time this
    // controller is invocated
    static ProcessTableController secondController;

    @FXML
    private TextField arrivalTime_Text;
    @FXML
    private TextField burstTime_Text;
    @FXML
    private TextField priority_Text;
    @FXML
    private Button addProcee_btn;
    @FXML
    private Button cancel_btn;
    @FXML
    private Label PriorityIndication_Label;

    @FXML
    AnchorPane rootPane;

    private Process newProcess;
    int Pid = 1;

    Stage stage;

    // show/hide priority text and label depending on the algorithm
    public void initialize(boolean show) {
        PriorityIndication_Label.setVisible(show);
        priority_Text.setVisible(show);
    }

    // event to add new process
    public void add(ActionEvent event) {
        // handle if the user forgeted to enter data in text fields
        if (arrivalTime_Text.getText().isEmpty()) {
            errorDialog("Arrival Time text field can't be empty.");
            return;
        }
        if (burstTime_Text.getText().isEmpty()) {
            errorDialog("Burst Time text field can't be empty.");
            return;
        }

        // handle if user entered non-integer value in any data field
        try {
            Integer.parseInt(burstTime_Text.getText());
        } catch (Exception e) {
            errorDialog("burst time must be Integer");
            return;
        }
        try {
            Integer.parseInt(arrivalTime_Text.getText());
        } catch (Exception e) {
            errorDialog("arrival time must be Integer");
            return;
        }
        try {
            Integer.parseInt(priority_Text.getText());
        } catch (Exception e) {
            errorDialog("periority must be Integer");
            return;
        }
        // create a new process with data entered
        newProcess = new Process(Pid++, Integer.parseInt(burstTime_Text.getText()),
                Integer.parseInt(arrivalTime_Text.getText()), Integer.parseInt(priority_Text.getText()));
        // add process to the table
        secondController.addProccessToMyList(newProcess);
    }

    static void setController(ProcessTableController con) {
        secondController = con;
    }

    private void errorDialog(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }

    public void Cancel(ActionEvent event) {
        stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
}