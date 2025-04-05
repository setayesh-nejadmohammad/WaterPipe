package game.waterpipe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PipeGame extends Application {
    private static final int SIZE = 5;
    private static final int CELL_SIZE = 80;
    private static final int SCENE_SIZE = 700;

    @Override
    public void start(Stage stage) {
        GridPane grid = new GridPane();
        grid.setLayoutX((SCENE_SIZE - (CELL_SIZE * SIZE)) / 2);
        grid.setLayoutY((SCENE_SIZE - (CELL_SIZE * SIZE)) / 2);

        // Sample pipe layout
        int[][] pipes = {
                {1, 2, 3, 0, 1},
                {4, 5, 2, 1, 0},
                {1, 0, 0, 6, 3},
                {4, 3, 2, 1, 0},
                {6, 0, 2, 1, 4}
        };

        // Load images once
        Image[] pipeImages = new Image[7];
        for (int i = 0; i <= 6; i++) {
            pipeImages[i] = new Image("D:/FUM/projects/waterpipe/src/pic/Pipes/pipe" + i + ".JPG");
        }

        // Create grid with clickable pipes
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                ImageView pipeView = new ImageView(pipeImages[pipes[row][col]]);
                pipeView.setFitWidth(CELL_SIZE);
                pipeView.setFitHeight(CELL_SIZE);

                // Set rotation pivot to center
                pipeView.setTranslateX(CELL_SIZE/2);
                pipeView.setTranslateY(CELL_SIZE/2);
                pipeView.setRotate(0);

                // Add click handler
                int finalRow = row;
                int finalCol = col;
                pipeView.setOnMouseClicked(event -> {
                    // Rotate 90 degrees on click
                    pipeView.setRotate(pipeView.getRotate() + 90);

                    // Keep rotation within 0-360 degrees
                    if (pipeView.getRotate() >= 360) {
                        pipeView.setRotate(0);
                    }

                    // Update your game state/logic here
                    System.out.println("Pipe at (" + finalRow + "," + finalCol + ") rotated to " + pipeView.getRotate() + "°");
                });

                grid.add(pipeView, col, row);
            }
        }

        Pane root = new Pane(grid);
        Scene scene = new Scene(root, SCENE_SIZE, SCENE_SIZE);
        stage.setScene(scene);
        stage.setTitle("Water Pipe Game");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
