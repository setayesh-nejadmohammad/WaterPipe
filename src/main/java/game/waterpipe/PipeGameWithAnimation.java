package game.waterpipe;
import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PipeGameWithAnimation extends Application {
    private static final int SIZE = 5;
    private static final int CELL_SIZE = 80;
    private static final int SCENE_SIZE = 700;
    private int[][] rotations = new int[SIZE][SIZE]; // Track rotation states (0-3)

    @Override
    public void start(Stage stage) throws Exception {
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
        for(int i = 0; i< 5; i++){
            for(int j = 0; j< 5; j++){
                System.out.print(pipes[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
        // Load images once
        Image[] pipeImages = new Image[7];
        for (int i = 0; i <= 6; i++) {
            pipeImages[i] = new Image("D:/FUM/projects/waterpipe/src/pic/Pipes/pipe" + i + ".JPG");
        }

        // Create grid with animated pipes
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                ImageView pipeView = new ImageView(pipeImages[pipes[row][col]]);
                pipeView.setFitWidth(CELL_SIZE);
                pipeView.setFitHeight(CELL_SIZE);

                // Center the rotation point
                pipeView.setTranslateX(CELL_SIZE/2);
                pipeView.setTranslateY(CELL_SIZE/2);

                // Make pipes more interactive
                pipeView.setStyle("-fx-cursor: hand;");
                pipeView.setOnMouseEntered(e -> pipeView.setStyle("-fx-effect: dropshadow(gaussian, #1598ec, 15, 0.7, 0, 0); -fx-cursor: hand;"));
                pipeView.setOnMouseExited(e -> pipeView.setStyle("-fx-effect: null; -fx-cursor: hand;"));

                // Store row and col in final variables for use in lambda
                final int r = row;
                final int c = col;

                pipeView.setOnMouseClicked(event -> {
                    // Disable during animation to prevent spam clicks
                    pipeView.setDisable(true);

                    // Create rotation animation
                    RotateTransition rt = new RotateTransition(Duration.millis(300), pipeView);
                    rt.setByAngle(90); // Rotate by 90 degrees
                    rt.setCycleCount(1);

                    // Update rotation state after animation completes
                    rt.setOnFinished(e -> {
                        rotations[r][c] = (rotations[r][c] + 1) % 4;
                        pipeView.setDisable(false);
                        System.out.printf("Pipe at (%d,%d) now at %d°%n",
                                r, c, rotations[r][c] * 90);
                    });

                    rt.play(); // Start the animation

                    // Update the pipe array
                    if(pipes[r][c] == 1){
                        pipes[r][c] = 2;
                    }
                    else if(pipes[r][c] == 2){
                        pipes[r][c] = 1;
                    }
                    else if(pipes[r][c] == 3){
                        pipes[r][c] = 4;
                    }
                    else if(pipes[r][c] == 4){
                        pipes[r][c] = 5;
                    }
                    else if(pipes[r][c] == 5){
                        pipes[r][c] = 6;
                    }
                    else if(pipes[r][c] == 6){
                        pipes[r][c] = 3;
                    }
                    for(int i = 0; i< 5; i++){
                        for(int j = 0; j< 5; j++){
                            System.out.print(pipes[i][j]+" ");
                        }
                        System.out.println();
                    }
                    System.out.println();
                });

                grid.add(pipeView, col, row);
            }
        }

        Pane root = new Pane(grid);
        Scene scene = new Scene(root, SCENE_SIZE, SCENE_SIZE);
        stage.setScene(scene);
        stage.setTitle("Water Pipe Game - Animated");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}