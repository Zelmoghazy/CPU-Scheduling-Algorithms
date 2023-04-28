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

public class AddProcessController {
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
    
    public void initialize(boolean show) {
    	PriorityIndication_Label.setVisible(show);
    	priority_Text.setVisible(show);
    }
    
    public void add(ActionEvent event) {
    	if (arrivalTime_Text.getText().isEmpty()) {
            errorDialog("Arrival Time text field can't be empty.");
            return;
        }
    	if (burstTime_Text.getText().isEmpty()) {
            errorDialog("Burst Time text field can't be empty.");
            return;
        }
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
    	newProcess = new Process(Pid++, Integer.parseInt(burstTime_Text.getText()) , Integer.parseInt(arrivalTime_Text.getText()), Integer.parseInt(priority_Text.getText()));
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
    /**
     * Initializes the controller class.
     */
//
//    public void sceneInitialization(SchedulerSimulationController ctrl, PCB process, boolean isEdit) {
//        parentController = ctrl;
//        edit_new = isEdit;
//        newProcess = process;
//        pID_Label.setText(Integer.toString(newProcess.getPID()));
//        pColor_Label.setBackground(new Background(new BackgroundFill(newProcess.getColor(), CornerRadii.EMPTY, Insets.EMPTY)));
//        if (isEdit == true) {
//            arrivalTime_Text.setText(Integer.toString(newProcess.getArrivalTime()));
//            burstTime_Text.setText(Integer.toString(newProcess.getBurstTime()));
//            priority_Text.setText(Integer.toString(newProcess.getPriority()));
//            addProcee_btn.setText("Edit");
//            parentController.setEditProcess(false);
//        } else {
//            addProcee_btn.setText("Add");
//            parentController.setNewProcess(false);
//        }
//        setTextFieldValidation();
//        currentScheduler = parentController.getCurrentScheduler();
//        if ((currentScheduler == SchedulerSimulationController.schedulerType.FCFS)
//                || (currentScheduler == SchedulerSimulationController.schedulerType.SJF_Preemptive_FCFS)
//                || (currentScheduler == SchedulerSimulationController.schedulerType.SJF_NonPreemptive_FCFS)
//                || (currentScheduler == SchedulerSimulationController.schedulerType.RoundRobin)) {
//            PriorityIndication_Label.setVisible(false);
//            priority_Text.setVisible(false);
//        }
//    }
//
//    private void setTextFieldValidation() {
//        UnaryOperator<Change> filter = change -> {
//            String text = change.getText();
//
//            if (text.matches("[0-9]*")) {
//                return change;
//            }
//
//            return null;
//        };
//        TextFormatter<String> textFormatter1 = new TextFormatter<>(filter);
//        arrivalTime_Text.setTextFormatter(textFormatter1);
//        TextFormatter<String> textFormatter2 = new TextFormatter<>(filter);
//        burstTime_Text.setTextFormatter(textFormatter2);
//        TextFormatter<String> textFormatter3 = new TextFormatter<>(filter);
//        priority_Text.setTextFormatter(textFormatter3);
//    }
//
//    /**
//     * For Open Error Messages.
//     *
//     * @param msg Question string
//     */
    
//
//    @FXML
//    private void addProcessButton_MouseEvent(MouseEvent event) {
//        setProcess();
//    }
//
//    @FXML
//    private void addProcessButton_KeyboardEvent(KeyEvent event) {
//        if (event.getCode().toString().equals("ENTER")) {
//            setProcess();
//        }
//    }
//
//    private void setProcess() {
//        if (arrivalTime_Text.getText().isEmpty()) {
//            errorDialog("Arrival Time text field can't be empty.");
//            return;
//        }
//        if (burstTime_Text.getText().isEmpty()) {
//            errorDialog("Burst Time text field can't be empty.");
//            return;
//        }
//
//        if ((currentScheduler == SchedulerSimulationController.schedulerType.FCFS)
//                || (currentScheduler == SchedulerSimulationController.schedulerType.SJF_Preemptive_FCFS)
//                || (currentScheduler == SchedulerSimulationController.schedulerType.SJF_NonPreemptive_FCFS)
//                || (currentScheduler == SchedulerSimulationController.schedulerType.RoundRobin)) {
//
//        } else {
//            if (priority_Text.getText().isEmpty()) {
//                errorDialog("Priority text field can't be empty.");
//                return;
//            }
//            newProcess.setPriority(Integer.valueOf(priority_Text.getText()));
//        }
//
//        newProcess.setArrivalTime(Integer.valueOf(arrivalTime_Text.getText()));
//        newProcess.setBurstTime(Integer.valueOf(burstTime_Text.getText()));
//        if (edit_new) {
//            parentController.setEditProcess(true);
//        } else {
//            parentController.setNewProcess(true);
//        }
//        sceneClose();
//    }
//
//    @FXML
//    private void cancelBuotton_MouseEvent(MouseEvent event) {
//        if (edit_new) {
//            parentController.setEditProcess(false);
//        } else {
//            parentController.setNewProcess(false);
//        }
//        sceneClose();
//    }
//
//    @FXML
//    private void cancelBuotton_KeyboardEvent(KeyEvent event
//    ) {
//        if (event.getCode().toString().equals("ENTER")) {
//            if (edit_new) {
//                parentController.setEditProcess(false);
//            } else {
//                parentController.setNewProcess(false);
//            }
//            sceneClose();
//        }
//    }
//
//    private void sceneClose() {
//        Stage stage = (Stage) cancel_btn.getScene().getWindow();
//        stage.close();
//    }

}