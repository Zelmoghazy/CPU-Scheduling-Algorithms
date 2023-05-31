package application;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SceneThreeController implements Initializable{

    @FXML
    private Label myLabel, turnarroundTimeLabel, waitingTimeLabel;
    
    @FXML
    private TableView<Process> myTable;
    
    @FXML
    private TableColumn<Process, Integer> id;

	@FXML
    private TableColumn<Process, Integer> arrival_time;

    @FXML
    private TableColumn<Process, Integer> burst_time;
    
    @FXML
    private TableColumn<Process, Integer> priority;

    @FXML
    private TableColumn<Process, Integer> waiting_time;

    @FXML
    private TableColumn<Process, Integer> turnarround_time;
    
    @FXML
    HBox myHBox;
    
    private Parent root;
	private Scene scene;
	private Stage stage;
    private Integer remaining_time;
    
    ArrayList<Process> list = new ArrayList<Process>();
    
	ObservableList<Process> ObList = FXCollections.observableList(list);

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		id.setCellValueFactory(new PropertyValueFactory<Process, Integer>("PID"));
		arrival_time.setCellValueFactory(new PropertyValueFactory<Process, Integer>("arrivalTime"));
		burst_time.setCellValueFactory(new PropertyValueFactory<Process, Integer>("burstTime"));
		waiting_time.setCellValueFactory(new PropertyValueFactory<Process, Integer>("waitingTime"));
		turnarround_time.setCellValueFactory(new PropertyValueFactory<Process, Integer>("turnAroundTime"));
		priority.setCellValueFactory(new PropertyValueFactory<Process, Integer>("priority"));
		
	}
	public void initGanttChart(ArrayList<GanttChartBar> ganttChartBars) {
		int index = 1;
		for (GanttChartBar ganttChartBar : ganttChartBars) 
        {
            for (int i = 0; i < ganttChartBar.getEndTime() - ganttChartBar.getStartTime(); i++) 
            {
                StackPane stack = new StackPane();
                Rectangle rectangle = createRectangle(ganttChartBar.getProcess().getPID());
                Text h;
                if(ganttChartBar.getProcess().getPID() == -1) {
                	h = new Text("Idle");
                }else
                	h = new Text("P"+ ganttChartBar.getProcess().getPID() + "\n" + index);
                stack.getChildren().addAll(rectangle, h);
                myHBox.getChildren().add(stack);
                index++;
            }
        }
	}
	
	private Rectangle createRectangle(int i) 
    {
        double width = 25; // Width between 100 and 200 pixels
        double height = 50; // Height between 50 and 100 pixels
        Random random = new Random(i);
        Color color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        Rectangle rectangle = new Rectangle(width, height, color);
        return rectangle;
    }
	public void setTableValues(ArrayList<Process> list) {
		this.list.addAll(list);
		myTable.setItems(ObList);
		
		if(myLabel.getText().contains("Priority")) {
			priority.setVisible(true);
		}else {
			priority.setVisible(false);
		}		
	}
	
	public void setLabel(String selection) {
		this.myLabel.setText(selection);
	}
	public void setTurnArroundTime(String str) {
		turnarroundTimeLabel.setText(str);
	}
	public void setWaitingTime(String str) {
		waitingTimeLabel.setText(str);
	}
	
	public void switchToStart(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene1.fxml"));
		root = loader.load();
		SceneOneController nextController = loader.getController();
		scene = new Scene(root);
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

}
