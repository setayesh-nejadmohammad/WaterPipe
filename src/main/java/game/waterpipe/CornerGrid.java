package game.waterpipe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class CornerGrid extends Application {
    private static final int GRID_SIZE = 5; // 5x5 grid
    private static final int CELL_SIZE = 50; // Size of each square cell
    private static final int SCENE_SIZE = 700; // 700x700 scene

    @Override
    public void start(Stage primaryStage) {
        // Create the grid container
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER); // Align to top-left corner

        // Create 5x5 grid of squares
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
                rect.setFill(Color.WHITE);
                rect.setStroke(Color.BLACK);
                rect.setStrokeWidth(1);
                grid.add(rect, col, row);
            }
        }

        // Create the root pane with padding to position the grid
        StackPane root = new StackPane();
        root.setPadding(new Insets(10)); // 10px padding from all sides

        // Add the grid to the root pane (aligned to top-left)
        //StackPane.setAlignment(grid, Pos.TOP_LEFT);
        root.getChildren().add(grid);

        // Create the scene
        Scene scene = new Scene(root, SCENE_SIZE, SCENE_SIZE);

        primaryStage.setTitle("5x5 Grid in Corner");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
