package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

//controller for add process in live
public class addLiveProcessController {

    @FXML
    private Button addProcee_btn;

    @FXML
    TextField burstTime_Text, PID, priority_Text;

    @FXML
    private Button cancel_btn;

    @FXML
    private AnchorPane rootPane;

    @FXML
    Label PriorityLabel;

    Stage stage;

    private Process newProcess;
    // a static object to add processes to the same object every time this
    // controller is invocated
    static LiveSceneController secondController;

    // event to add new process
    public void add(ActionEvent event) {
        // handle if the user forgeted to enter data in text fields
        if (burstTime_Text.getText().isEmpty()) {
            errorDialog("Burst Time text field can't be empty.");
            return;
        }
        if (PID.getText().isEmpty()) {
            errorDialog("Process ID text field can't be empty.");
            return;
        }

        // handle if user entered non-integer value in any data field
        try {
            Integer.parseInt(PID.getText());
        } catch (Exception e) {
            errorDialog("process ID must be Integer");
            return;
        }
        try {
            Integer.parseInt(burstTime_Text.getText());
        } catch (Exception e) {
            errorDialog("burst time must be Integer");
            return;
        }
        try {
            Integer.parseInt(priority_Text.getText());
        } catch (Exception e) {
            errorDialog("priority must be Integer");
            return;
        }

        // create a new process with data entered
        newProcess = new Process(Integer.parseInt(PID.getText()), Integer.parseInt(burstTime_Text.getText()), 0,
                Integer.parseInt(priority_Text.getText()));

        // add process to the live scene
        secondController.addProccessToMyList(newProcess);
    }

    @FXML
    void Cancel(ActionEvent event) {
        stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    private void errorDialog(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }

    static void setController(LiveSceneController con) {
        secondController = con;
    }

    // show/hide priority text and label depending on the algorithm
    public void initLabelAndText(boolean visible) {
        PriorityLabel.setText("Priority");
        PriorityLabel.setVisible(visible);
        PriorityLabel.setDisable(!visible);
        priority_Text.setVisible(visible);
        priority_Text.setDisable(!visible);
    }

}
