package game.waterpipe;

import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;

import java.io.IOException;

public class HelloTest extends Application {
    private int SIZE = 5; // 5x5 grid --> This will change if user picks LEVEL 2
    private static final int CELL_SIZE = 70; // Size of each square cell
    private static final int SCENE_SIZE = 700; // 700x700 scene
    private int[][] rotations = new int[SIZE][SIZE]; // Track rotation states (0-3)
    private double waterLevel = -CELL_SIZE; // Starting level of the water animation
    private int[][] pipes;
    private Image icon = new Image(getClass().getResourceAsStream("pics/water-pipe.png"));
    private AnimationTimer waterAnimation;

    @Override
    public void start(Stage stage) throws Exception {
        Stage stageSelector = new Stage();
        stageSelector.getIcons().add(icon);
        stageSelector.setTitle("Level Chooser");
        Label label = new Label("Choose Level :");
        label.getStyleClass().add("text");

        Image level1 = new Image(getClass().getResourceAsStream("pics/level1.png"));
        ImageView level1view = new ImageView(level1);
        Image level2 = new Image(getClass().getResourceAsStream("pics/level2.png"));
        ImageView level2view = new ImageView(level2);

        Button button1 = new Button();
        Button button2 = new Button();
        button1.setGraphic(level1view);
        button2.setGraphic(level2view);

        button1.getStyleClass().add("button");
        button2.getStyleClass().add("button");

        button1.setOnAction(e -> {
            startGame(1);
            stageSelector.close();
        });
        button2.setOnAction(e -> {
            startGame(2);
            stageSelector.close();
        });

        HBox buttonsLayout = new HBox(10);
        buttonsLayout.setAlignment(Pos.CENTER);
        buttonsLayout.getChildren().addAll(button1, button2);

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, buttonsLayout);
        layout.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene scene1 = new Scene(layout, SCENE_SIZE, SCENE_SIZE);
        scene1.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stageSelector.setScene(scene1);
        stageSelector.show();
    }

    public void startGame(int level){
        Stage stage = new Stage();
        if(level == 1){
            SIZE = 5;
        }
        else if(level == 2){
            SIZE = 7;
        }

        // Calculate center position
        double centerX = (SCENE_SIZE - (CELL_SIZE * SIZE)) / 2;
        double centerY = (SCENE_SIZE - (CELL_SIZE * SIZE)) / 2;

        GridPane grid = new GridPane();
        grid.setGridLinesVisible(false); // We'll draw our own borders
        grid.setLayoutX(centerX);
        grid.setLayoutY(centerY);
        grid.setPadding(new Insets(0)); // Remove internal padding
        grid.setHgap(0); // Remove horizontal gap
        grid.setVgap(0); // Remove vertical gap

        // Sample pipe layout
        int[][] pipes1 = {
                {7, 0, 0, 0, 0},
                {6, 1, 1, 3, 0},
                {5, 1, 2, 4, 0},
                {1, 0, 0, 0, 0},
                {4, 2, 1, 1, 8}
        };

        int[][] pipes2 = {
                {7, 0, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 0, 0},
                {2, 0, 0, 0, 0, 0, 0},
                {4, 1, 2, 1, 6, 0, 0},
                {0, 3, 1, 2, 4, 0, 0},
                {0, 1, 0, 0, 0, 0, 0},
                {0, 4, 2, 1, 2, 1, 8}
        };

        pipes = (level == 1) ? pipes1 : pipes2;

        Button startButton = new Button(); // Start to check the pipes' order
        startButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("pics/start.png"))));
        startButton.getStyleClass().add("button");
        startButton.setLayoutX(600);
        startButton.setLayoutY(20);

        startButton.setOnAction(e -> { /* Handle game start */ });

        // Load pipe images
        Image[] pipeImages = new Image[10];
        for (int i = 0; i <= 8; i++) {
            pipeImages[i] = new Image(getClass().getResourceAsStream("pics/pipe" + i + ".png"));
        }

        // Create grid with pipes
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                ImageView pipeView = new ImageView(pipeImages[pipes[row][col]]);
                pipeView.setFitWidth(CELL_SIZE);
                pipeView.setFitHeight(CELL_SIZE);

                grid.add(pipeView, col, row);
            }
        }

        // Create the root pane with padding to position the grid
        Pane root = new Pane(grid);
        root.getChildren().add(startButton);
        root.setStyle("-fx-background-color: lightblue;");
        Scene scene = new Scene(root, SCENE_SIZE, SCENE_SIZE, Color.LIGHTBLUE);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("Water Pipe");
        stage.setScene(scene);
        stage.show();

        // Initialize water animation
        initializeWaterAnimation(grid);
    }

    private void initializeWaterAnimation(GridPane grid) {
        waterAnimation = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateWaterLevel(grid);
            }
        };
        waterAnimation.start();
    }

    private void updateWaterLevel(GridPane grid) {
        // Move water level down the pipes
        if (waterLevel < grid.getHeight()) {
            waterLevel += 1; // Increase water level
        } else {
            waterLevel = -CELL_SIZE; // Reset to the beginning
        }

        // Draw water rectangles over the pipes
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                ImageView pipeView = (ImageView) getNodeByRowColumnIndex(row, col, grid);
                if (pipeView != null) {
                    if (pipes[row][col] != 0 && waterLevel >= (row * CELL_SIZE) && waterLevel < ((row + 1) * CELL_SIZE)) {
                        // Draw water on top of the pipe if it is within water level range
                        Rectangle water = new Rectangle(CELL_SIZE, CELL_SIZE);
                        water.setFill(Color.BLUE.deriveColor(0, 1, 1, 0.5)); // Semi-transparent water
                        water.setTranslateY(waterLevel - (row * CELL_SIZE));
                        GridPane.setRowIndex(water, row);
                        GridPane.setColumnIndex(water, col);
                        if (!grid.getChildren().contains(water)) {
                            grid.getChildren().add(water);
                        }
                    }
                }
            }
        }
    }

    private Node getNodeByRowColumnIndex(int row, int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        launch();
    }
}
