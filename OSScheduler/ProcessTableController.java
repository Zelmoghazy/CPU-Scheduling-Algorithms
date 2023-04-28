package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ProcessTableController implements Initializable{	
	 	
		@FXML
		private TableView<Process> myTable;
	
    	@FXML
	    private TableColumn<Process, Integer> PID;

	    @FXML
	    private TableColumn<Process, Integer> arrivalTime;

	    @FXML
	    private TableColumn<Process, Integer> burstTime;

	    @FXML
	    private TableColumn<Process, Integer> periority;

	    @FXML
	    private Label myLabel;

	    @FXML
	    private CheckBox myCheckBox;
	    
	    ArrayList<Process> list = new ArrayList<Process>();
	    
		ObservableList<Process> ObList = FXCollections.observableList(list);
		
		
		
	    
	    private Parent root;
		private Scene scene;
		private Stage stage;
		private int quantum; 
		private double turnArround,waiting;
		
	    
	    public void setLabel(String data) {
	    	this.myLabel.setText(data);
	    	if(data.contains("Priority")) {
	    		periority.setVisible(true);
	    	}else {
	    		periority.setVisible(false);
	    	}
	    }
	    
	    public void addProcess(ActionEvent event) throws IOException {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("addProcess.fxml"));
			root = loader.load();
			AddProcessController secondController = loader.getController();
			if(myLabel.getText().contains("Priority")) {
				secondController.initialize(true);
			}else {
				secondController.initialize(false);
			}
			
			Stage myStage = new Stage();
			myStage.setTitle("Add Process");
			myStage.setScene(new Scene(root));
			myStage.show();
	    }
	    
	    public void next(ActionEvent event) throws IOException {
	    	ArrayList<GanttChartBar> ganttChartBars ;
	    	SchedulingAlgorithm algo;
	    	if(myLabel.getText().equals("FCFS")) {
		    	algo = new FCFS(list);
				ganttChartBars =((FCFS) algo).getGanttChartBars();
		 	}
	    	else if(myLabel.getText().equals("SJF Preemptive")) {
	    		algo = new SJFPreemptive(list);
	    		ganttChartBars = ((SJFPreemptive)algo).getGanttChartBars();
	    	}
	    	else if(myLabel.getText().equals("SJF NON-Preamptive")) {
	    		algo = new SJF(list);
	    		ganttChartBars = ((SJF) algo).getGanttChartBars();
	    	}
	    	else if(myLabel.getText().equals("Round Robin")) {
	    		algo = new RoundRobin(list,quantum);
	    		ganttChartBars = ((RoundRobin)algo).getGanttChartBars();
	    	}
	    	else if(myLabel.getText().equals("Priority Preemptive")) {
	    		algo = new PriorityPreemptive(list);
	    		ganttChartBars = ((PriorityPreemptive)algo).getGanttChartBars();
	    	}
	    	else {
	    		algo = new Priority(list);
	    		ganttChartBars = ((Priority) algo).getGanttChartBars();
	    	}
			waiting = algo.averageWaitingTime;
			turnArround = algo.averageTurnAroundTime;
			
	    	if(myCheckBox.isSelected()) {
	    		FXMLLoader loader = new FXMLLoader(getClass().getResource("LiveScene.fxml"));
				root = loader.load();
				LiveSceneController secondController = loader.getController();
				addLiveProcessController.setController(secondController);
				if(myLabel.getText().equals("Round Robin")) {
					secondController.setQuantum(quantum);
				}
				secondController.setLabel(myLabel.getText());
				secondController.initGanttChart(ganttChartBars);
				secondController.setTableValues(list);
				scene = new Scene(root);
				stage = (Stage)((Node) event.getSource()).getScene().getWindow();
				stage.setScene(scene);
				stage.show();
	    	}else {
	    		FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene3.fxml"));
				root = loader.load();
				SceneThreeController secondController = loader.getController();
				secondController.setLabel(myLabel.getText());
				secondController.setTurnArroundTime("average turnarround time = " + turnArround);
				secondController.setWaitingTime("average waiting time = " + waiting);
				secondController.setTableValues(list);				
				secondController.initGanttChart(ganttChartBars);
				scene = new Scene(root);
				stage = (Stage)((Node) event.getSource()).getScene().getWindow();
				stage.setScene(scene);
				stage.show();
	    	}
	    }
	    
	    public void addProccessToMyList(Process newProcess) {
	    	list.add(newProcess);
	    	myTable.getItems().add(newProcess);
	    }
	   
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
			PID.setCellValueFactory(new PropertyValueFactory<Process, Integer>("PID"));
			arrivalTime.setCellValueFactory(new PropertyValueFactory<Process, Integer>("arrivalTime"));
			burstTime.setCellValueFactory(new PropertyValueFactory<Process, Integer>("burstTime"));
			periority.setCellValueFactory(new PropertyValueFactory<Process, Integer>("priority"));
			
		}
		public void setQuantum(int quantum) {
			this.quantum = quantum;
		}
		
}