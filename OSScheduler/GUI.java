import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application
{
    @Override
    public void start(Stage s1) 
    {
        HBox root = new HBox(0); // 10 pixels of spacing between rectangles
        ArrayList<Process> processes = new ArrayList<>();
        processes.add(new Process(1, 5, 0));
        processes.add(new Process(2, 3, 1));
        processes.add(new Process(3, 1, 2));
        processes.add(new Process(4, 2, 3));
        processes.add(new Process(5, 3, 4));

        RoundRobin rr = new RoundRobin(processes, 2);
        ArrayList<GanttChartBar> ganttChartBars = rr.getGanttChartBars();
        
        int index = 1;
        for (GanttChartBar ganttChartBar : ganttChartBars) 
        {
            for (int i = 0; i < ganttChartBar.getEndTime() - ganttChartBar.getStartTime(); i++) 
            {
                StackPane stack = new StackPane();
                Rectangle rectangle = createRectangle(ganttChartBar.getProcess().getPID());
                Text h = new Text("P"+ ganttChartBar.getProcess().getPID() + "\n" + index);
                stack.getChildren().addAll(rectangle, h);
                root.getChildren().add(stack);
                index++;
            }
        }
        // Create a scene with the layout pane as the root node
        Scene scene = new Scene(root, 800, 350); // Adjust scene width to fit both rectangles

        // Set the scene on the primary stage and show it
        s1.setScene(scene);
        s1.show();
    }
    public static void main(String[] args) {
        launch(args);
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

}
    

