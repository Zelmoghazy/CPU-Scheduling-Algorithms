package application;

 

import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class LiveScene2Controller implements Initializable{
	private int stop_value, 
    index = 0;
    Map<Process, Integer> burst_count = new HashMap<>();
    ArrayList<Process> processes = new ArrayList<>();
    ObservableList<Process> ObList = FXCollections.observableList(processes);
    ArrayList<GanttChartBar> ganttChartBars;
    private Parent rootTwo;
	private Scene scene;
	private Stage stage;
    
	@FXML
	private TableView<Process> myTable;
	
    @FXML
    HBox root;
    
    @FXML
    Button addProcessBTN;
    
    @FXML
    private TableColumn<Process, Integer> Pid;

    @FXML
    private TableColumn<Process, Integer> arrivalTime;

    @FXML
    private TableColumn<Process, Integer> burstTime;

    @FXML
    private Label myLabel;

    @FXML
    private TableColumn<LiveSceneController, Integer> remainingCPUTime;
    
    @FXML
    private TableView<LiveSceneController> myTable2;
    
    Timeline dynamic_gunt = new Timeline();
    private Parent root2;
    private int quantum;
    private Integer remaining_time;
    
    public void setLabel(String str) {
    	myLabel.setText(str);
    }
    
    public void setQuantum(int q) {
    	quantum = q;
    }
    
    public void addProccessToMyList(Process newProcess) {
    	newProcess.setArrivalTime(stop_value);
    	
    	processes.add(newProcess);
    	
    	SchedulingAlgorithm algo;
    	if(myLabel.getText().equals("FCFS")) {
	    	algo = new FCFS(processes);
			ganttChartBars =((FCFS) algo).getGanttChartBars();
	 	}
    	else if(myLabel.getText().equals("SJF Preemptive")) {
    		algo = new SJFPreemptive(processes);
    		ganttChartBars = ((SJFPreemptive)algo).getGanttChartBars();
    	}
    	else if(myLabel.getText().equals("SJF NON-Preamptive")) {
    		algo = new SJF(processes);
    		ganttChartBars = ((SJF) algo).getGanttChartBars();
    	}
    	else if(myLabel.getText().equals("Round Robin")) {
    		algo = new RoundRobin(processes,quantum);
    		ganttChartBars = ((RoundRobin)algo).getGanttChartBars();
    	}
    	else if(myLabel.getText().equals("Priority Preemptive")) {
    		algo = new PriorityPreemptive(processes);
    		ganttChartBars = ((PriorityPreemptive)algo).getGanttChartBars();
    	}
    	else {
    		algo = new Priority(processes);
    		ganttChartBars = ((Priority) algo).getGanttChartBars();
    	}
    	processes.remove(newProcess);
    	myTable.getItems().add(newProcess);
    }
    
    public void setTableValues(ArrayList<Process> list) {
    	processes.clear();
    	processes.addAll(list);
    	myTable.setItems(ObList);
    }
    
    public void pause(ActionEvent event) throws IOException {
    	stop_value += (int) dynamic_gunt.getCurrentTime().toSeconds();
        dynamic_gunt.pause();
        dynamic_gunt.getKeyFrames().clear();
        addProcessBTN.setDisable(false);
    }
    public void play(ActionEvent event) throws IOException {
    	
    	addProcessBTN.setDisable(true);
    	
        for (GanttChartBar ganttChartBar : ganttChartBars) {
            burst_count.put(ganttChartBar.getProcess(), ganttChartBar.getProcess().getBurstTime());
        }

        int elapsed = 1;
        for (GanttChartBar ganttChartBar : ganttChartBars) {
            if (elapsed >= stop_value){
                break;
            }
            for (int i = 0; i < ganttChartBar.getDuration(); i++) {
                if (elapsed >= stop_value) {
                    break;
                }
                burst_count.put(ganttChartBar.getProcess(), burst_count.get(ganttChartBar.getProcess()) - 1);
                elapsed++;
            }
            
        }

        int index = 0;
        for (GanttChartBar ganttChartBar : ganttChartBars) {
            for (int i = 0; i < ganttChartBar.getDuration(); i++) {
                // System.out.println(ganttChartBar.getDuration());
                index++;
                if (index < stop_value) {
                    continue;
                } else {
                    StackPane stack = new StackPane();
                    Rectangle rectangle = createRectangle(ganttChartBar.getProcess().getPID());
                    remaining_time = burst_count.get(ganttChartBar.getProcess()) ;
                    Text h;
                    Text h2;
                    if(ganttChartBar.getProcess().getPID() == -1) {
                    	h= new Text("Idle\n\n");
                    }else {
                    	h = new Text("P"+ ganttChartBar.getProcess().getPID() + "\n\n");
                    }
                    h2 = new Text(remaining_time.toString());
                    burst_count.put(ganttChartBar.getProcess(),
                            burst_count.get(ganttChartBar.getProcess()) - 1);
                    KeyFrame kf = new KeyFrame(Duration.seconds(index - stop_value + 1), actionEvent -> {
                        stack.getChildren().addAll(rectangle, h,h2);
                        root.getChildren().add(stack);
                        
                    });
                    dynamic_gunt.getKeyFrames().add(kf);
                }
            }
        }
        dynamic_gunt.playFromStart();
    }
    public void addProcess(ActionEvent event) throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("addLiveProcess.fxml"));
		root2 = loader.load();
		addLiveProcessController secondController = loader.getController();
		
		
		Stage myStage = new Stage();
		myStage.setTitle("Add Process");
		myStage.setScene(new Scene(root2));
		myStage.show();
		if(myLabel.getText().contains("Priority")) {
			secondController.initLabelAndText(true);
		}else {
			secondController.initLabelAndText(false);
		}
    }
    
    public void initGanttChart(ArrayList<GanttChartBar> in) {
    	ganttChartBars = in;
        for (GanttChartBar ganttChartBar : ganttChartBars) {
            burst_count.put(ganttChartBar.getProcess(), ganttChartBar.getProcess().getBurstTime());
        }
        
        stop_value = 0;
        for (GanttChartBar ganttChartBar : ganttChartBars) {
            for (int i = 0; i < ganttChartBar.getDuration(); i++) {
                index++;
                if (index < stop_value) {
                    continue;
                } else {
                    StackPane stack = new StackPane();
                    Rectangle rectangle = createRectangle(ganttChartBar.getProcess().getPID());
                    remaining_time = burst_count.get(ganttChartBar.getProcess()) ;
                    Text h2 = new Text(remaining_time.toString());
                    Text h;
                    if(ganttChartBar.getProcess().getPID() == -1) {
                    	h= new Text("Idle\n\n");
                    }else {
                    	h = new Text("P"+ ganttChartBar.getProcess().getPID() + "\n\n");
                    }
                    burst_count.put(ganttChartBar.getProcess(), burst_count.get(ganttChartBar.getProcess()) - 1);
                    
                    KeyFrame kf = new KeyFrame(Duration.seconds(index + 1), actionEvent -> {
                    	stack.getChildren().addAll(rectangle, h , h2);
                        root.getChildren().add(stack);

                    });
                    dynamic_gunt.getKeyFrames().add(kf);
                }
            }

        }
        dynamic_gunt.play();

        //wa2feen hena


//        root.getChildren().addAll(button, button2);
//        Scene scene = new Scene(root, 800, 350);
//        s1.setScene(scene);
//        s1.show();
    }

    private Rectangle createRectangle(int i) {
        double width = 25; // Width between 100 and 200 pixels
        double height = 50; // Height between 50 and 100 pixels
        Random random = new Random(i + 2);
        Color color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        Rectangle rectangle = new Rectangle(width, height, color);
        return rectangle;
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Pid.setCellValueFactory(new PropertyValueFactory<Process, Integer>("PID"));
		arrivalTime.setCellValueFactory(new PropertyValueFactory<Process, Integer>("arrivalTime"));
		burstTime.setCellValueFactory(new PropertyValueFactory<Process, Integer>("burstTime"));
	}
	
	public void switchToStart(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene1.fxml"));
		rootTwo = loader.load();
		SceneOneController nextController = loader.getController();
		scene = new Scene(rootTwo);
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
	
	
}
