
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AnimationTest extends Application {
    private int stop_value;
    Map<Process, Integer> burst_count = new HashMap<>();

    @Override
    public void start(Stage s1) {
        HBox root = new HBox(0); 

        ArrayList<Process> processes = new ArrayList<>();
        processes.add(new Process(1, 5, 0));
        processes.add(new Process(2, 3, 1));
        processes.add(new Process(3, 1, 2));
        processes.add(new Process(4, 2, 3));
        processes.add(new Process(5, 3, 4));



        Timeline dynamicGantt = new Timeline();

        int time_quantum = 2;
        RoundRobin rr = new RoundRobin(processes, time_quantum);
        ArrayList<GanttChartBar> ganttChartBars = rr.getGanttChartBars();

        for (GanttChartBar ganttChartBar : ganttChartBars) {
            burst_count.put(ganttChartBar.getProcess(), ganttChartBar.getProcess().getBurstTime());
        }
        stop_value = 0;
        int index = 0;

        for (GanttChartBar ganttChartBar : ganttChartBars) {
            for (int i = 0; i < ganttChartBar.getDuration(); i++) {
                index++;
                if (index < stop_value) {
                    continue;
                } else {
                    StackPane stack = new StackPane();
                    Rectangle rectangle = createRectangle(ganttChartBar.getProcess().getPID());
                    Text h = new Text(burst_count.get(ganttChartBar.getProcess()).toString() + "\n" + index);
                    // Text h = new Text("P"+ ganttChartBar.getProcess().getPID() + "\n" + index);
                    burst_count.put(ganttChartBar.getProcess(), burst_count.get(ganttChartBar.getProcess()) - 1);
                    KeyFrame kf = new KeyFrame(Duration.seconds(index + 1), actionEvent -> {

                        stack.getChildren().addAll(rectangle, h);
                        root.getChildren().add(stack);

                    });
                    dynamicGantt.getKeyFrames().add(kf);
                }
            }

        }
        dynamicGantt.play();

        Button button1 = new Button("Stop");
        Button button2 = new Button("Play");

        /* Stop Button Handler */
        EventHandler<ActionEvent> stopEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                stop_value += (int) dynamicGantt.getCurrentTime().toSeconds();
                dynamicGantt.pause();
                dynamicGantt.getKeyFrames().clear();
            }
        };

        button1.setOnAction(stopEvent);

        EventHandler<ActionEvent> playEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                // Timeline dynamicGantt = new Timeline();
                processes.add(new Process(6, 4, 3));
                RoundRobin rr = new RoundRobin(processes, time_quantum);
                ArrayList<GanttChartBar> ganttChartBars = rr.getGanttChartBars();

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
                        index++;
                        if (index < stop_value) {
                            continue;
                        } else {
                            StackPane stack = new StackPane();
                            Rectangle rectangle = createRectangle(ganttChartBar.getProcess().getPID());
                            Text h = new Text(burst_count.get(ganttChartBar.getProcess()).toString() + "\n" + index);
                            // Text h = new Text("P"+ ganttChartBar.getProcess().getPID() + "\n" + index);
                            burst_count.put(ganttChartBar.getProcess(),burst_count.get(ganttChartBar.getProcess()) - 1);
                            KeyFrame kf = new KeyFrame(Duration.seconds(index - stop_value + 1), actionEvent -> {
                                stack.getChildren().addAll(rectangle, h);
                                root.getChildren().add(stack);
                            });
                            dynamicGantt.getKeyFrames().add(kf);
                        }
                    }
                }
                dynamicGantt.playFromStart();
            }
        };

        button2.setOnAction(playEvent);

        root.getChildren().addAll(button1, button2);
        Scene scene = new Scene(root, 800, 350);
        s1.setScene(scene);
        s1.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Rectangle createRectangle(int i) {
        double width = 25; // Width between 100 and 200 pixels
        double height = 50; // Height between 50 and 100 pixels
        Random random = new Random(i + 2);
        Color color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        Rectangle rectangle = new Rectangle(width, height, color);
        return rectangle;
    }
}
