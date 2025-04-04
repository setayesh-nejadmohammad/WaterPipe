package game.waterpipe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SimpleGrid extends Application {
    private static final int SIZE = 5; // 5x5 grid
    private static final int CELL_SIZE = 50; // Size of each square cell

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(false); // We'll draw our own borders
        grid.setLayoutX(50);
        grid.setLayoutY(225);

        int[][] pipes = {
                {1, 2, 3, 0, 1},
                {4, 5, 2, 1, 0},
                {1, 0, 0, 6, 3},
                {4, 3, 2, 1, 0},
                {6, 0, 2, 1, 4}
        };

        // Create 5x5 grid of squares
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
                rect.setFill(Color.WHITE);
                rect.setStroke(Color.BLACK);
                rect.setStrokeWidth(1);
                grid.add(rect, col, row);
            }
        }
        // Load images once
        Image[] pipeImages = new Image[7];
        for (int i = 0; i <= 6; i++) {
            pipeImages[i] = new Image("D:/FUM/projects/waterpipe/src/pic/Pipes/pipe" + i + ".JPG");
        }

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                ImageView pipeView = new ImageView(pipeImages[pipes[row][col]]);
                pipeView.setFitWidth(CELL_SIZE);
                pipeView.setFitHeight(CELL_SIZE);
                pipeView.setPreserveRatio(true);
                pipeView.setSmooth(true);
                grid.add(pipeView, col, row);
            }
        }


        Pane pane = new Pane(grid);

        Scene scene = new Scene(pane, 700, 700);
        primaryStage.setTitle("5x5 Grid");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
