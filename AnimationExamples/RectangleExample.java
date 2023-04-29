import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class RectangleExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        HBox root = new HBox(10); // 10 pixels of spacing between rectangles

        for(int i = 0; i < 15; i++)
        {
            StackPane stack = new StackPane();
            Rectangle rectangle;
            if(i % 2 == 0){
                rectangle = new Rectangle(20, 100, Color.RED);
            }else
            {
                rectangle = new Rectangle(20, 100, Color.BLUE);
            }
          
            Text h = new Text(String.valueOf(i));
            stack.getChildren().addAll(rectangle,h);
            root.getChildren().addAll(stack);
        }
        // Create a scene with the layout pane as the root node
        Scene scene = new Scene(root, 800, 350); // Adjust scene width to fit both rectangles

        // Set the scene on the primary stage and show it
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
