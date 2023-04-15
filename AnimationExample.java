import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AnimationExample extends Application 
{
    private int rectCount = 0;

    @Override
    public void start(Stage primaryStage) {
        // Create a layout pane to hold the rectangles
        HBox root = new HBox(5); // 10 pixels of spacing between rectangles

        // Create a timeline that adds a new rectangle to the scene every 1 second
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, event -> {
                    Rectangle rectangle = createRectangle();
                    root.getChildren().add(rectangle);
                    rectCount++;
                }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(5); // Add 10 rectangles

        Scene scene = new Scene(root, 600, 150); 

        primaryStage.setScene(scene);
        primaryStage.show();
      
        timeline.play();
    }

    private Rectangle createRectangle() {
        double width = 50; // Width between 100 and 200 pixels
        double height = 100; // Height between 50 and 100 pixels
        Color color = Color.color(Math.random(), Math.random(), Math.random());
        Rectangle rectangle = new Rectangle(width, height, color);
        return rectangle;
    }

    public static void main(String[] args) 
    {
        launch(args);
    }

}
