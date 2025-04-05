package game.waterpipe;

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

import java.io.IOException;

public class HelloApplication extends Application {
    private int SIZE = 5; // 5x5 grid --> This will change if user pick LEVEVL 2
    private static final int CELL_SIZE = 70; // Size of each square cell
    private static final int SCENE_SIZE = 700; // 700x700 scene
    private int[][] rotations = new int[100][100]; // Track rotation states (0-3)
    private int moves = 10;
    Image icon = new Image(getClass().getResourceAsStream("pics/water-pipe.png"));

    //private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        //this.stage = stage;
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
        Label levelLabel = new Label(" LEVEL : " + level);
        levelLabel.getStyleClass().add("text");
        levelLabel.setLayoutX(300);
        levelLabel.setLayoutY(20);

        // Calculate center position
        double centerX = (SCENE_SIZE - (CELL_SIZE * SIZE)) / 2;
        double centerY = (SCENE_SIZE - (CELL_SIZE * SIZE)) / 2;

        final int[] moveArr = {moves};
        Label moveNum = new Label("Moves : " + moveArr[0]+ "");
        moveNum.setLayoutX(270);
        moveNum.setLayoutY(50);

        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(false); // We'll draw our own borders
        grid.setLayoutX(centerX);
        grid.setLayoutY(centerY);
        grid.setPadding(new Insets(0)); // Remove internal padding
        grid.setHgap(0); // Remove horizontal gap
        grid.setVgap(0); // Remove vertical gap

        Image background = new Image(getClass().getResourceAsStream("pics/background.png"));
        ImageView backgroundview = new ImageView(background);
        BackgroundImage backgroundImage = new BackgroundImage(
                background,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT  // می توانید BackgroundSize را تنظیم کنید
        );

        Background background1 = new Background(backgroundImage);
        //grid.setBackground(background1);

        // Create 5x5 grid of squares
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
                rect.setFill(Color.WHITE);
                rect.setStroke(Color.GRAY);
                rect.setStrokeWidth(1);
                grid.add(rect, col, row);
            }
        }

        stage.getIcons().add(icon); // Set an icon for the window

        // Sample pipe layout
        int[][] pipes3 = {
                {7, 0, 0, 0, 0},
                {4, 5, 2, 1, 0},
                {1, 0, 0, 6, 0},
                {4, 3, 2, 1, 0},
                {6, 0, 2, 1, 8}
        };
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

        int[][] pipes = (level == 1) ? pipes1 : pipes2;

        /*if(level == 1){
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    pipes[row][col] = pipes1[row][col];
                }
            }
        }*/

        wayCheck gameCheck = new wayCheck(pipes); // This will check if the user put pipes in right order

        Button startButton = new Button(); // Start to check the pipes' order
        startButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("pics/start.png"))));
        startButton.getStyleClass().add("button");
        startButton.setLayoutX(600);
        startButton.setLayoutY(20);

        startButton.setOnAction(e -> {
            moveArr[0] -= 1;
            System.out.println("moves = " + moveArr[0]);
            boolean result = gameCheck.stupidCheck(SIZE);
            Stage gameResult = new Stage();
            Label label;
            Pane root = new Pane();
            Scene gameResultScene;
            gameResult.setTitle("Game Result");

            if(result){
                label = new Label("YOU WIN!");
                gameResultScene = new Scene(root, 400, 200, Color.LIGHTBLUE);
                root.setStyle("-fx-background-color: green;");
            }
            else{
                label = new Label("GAME OVER!");
                gameResultScene = new Scene(root, 400, 200, Color.RED);
                root.setStyle("-fx-background-color: red;");
            }

            label.setLayoutX(100);
            label.setLayoutY(70);
            label.getStyleClass().add("text");
            label.setStyle("-fx-text-fill: white;");
            root.getChildren().add(label);
            root.getChildren().add(backgroundview);
            gameResultScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            gameResult.setScene(gameResultScene);
            gameResult.show();
        });


        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                System.out.print(pipes[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();

        // Load images once
        Image[] pipeImages = new Image[10];
        for (int i = 0; i <= 8; i++) {
            pipeImages[i] = new Image(getClass().getResourceAsStream(("pics/pipe" + i + ".png")));
        }
        Image[] staticPipes = new Image[10];
        for (int i = 1; i < 4; i++){
            staticPipes[i] = new Image(getClass().getResourceAsStream(("pics/static pipe" + i + ".png")));
        }

        // Create grid with animated pipes
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                ImageView pipeView;
                boolean flag = false;

                if(level == 2 && ((row == 3 && col == 2) || row == 5 && col == 1)){
                    pipeView = new ImageView(staticPipes[pipes[row][col]]);
                    flag = true;
                }
                else{
                    pipeView = new ImageView(pipeImages[pipes[row][col]]);
                }

                pipeView.setFitWidth(CELL_SIZE);
                pipeView.setFitHeight(CELL_SIZE);

                if(row == 0 || col == SIZE-1 || flag){
                    grid.add(pipeView, col, row);
                    continue;
                }


                // Make pipes more interactive
                pipeView.setStyle("-fx-cursor: hand;");
                pipeView.setOnMouseEntered(e -> pipeView.setStyle("-fx-effect: dropshadow(gaussian, #1598ec, 15, 0.7, 0, 0); -fx-cursor: hand;"));
                pipeView.setOnMouseExited(e -> pipeView.setStyle("-fx-effect: null; -fx-cursor: hand;"));

                // Store row and col in final variables for use in lambda
                final int r = row;
                final int c = col;

                pipeView.setOnMouseClicked(event -> {
                    if(level == 2) moves -= 1;
                    // Disable during animation to prevent spam clicks
                    pipeView.setDisable(true);

                    // Determine rotation direction based on mouse button
                    int rotationAngle = 90;
                    if (event.getButton() == MouseButton.SECONDARY) {
                        rotationAngle = -90; // Rotate counter-clockwise for right click
                    }


                    // Create rotation animation
                    RotateTransition rt = new RotateTransition(Duration.millis(300), pipeView);
                    rt.setByAngle(rotationAngle); // Rotate by 90 degrees
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
                        if(rotationAngle == 90) pipes[r][c] = 4;
                        else pipes[r][c] = 6;
                    }
                    else if(pipes[r][c] == 4){
                        if(rotationAngle == 90) pipes[r][c] = 5;
                        else pipes[r][c] = 3;
                    }
                    else if(pipes[r][c] == 5){
                        if(rotationAngle == 90) pipes[r][c] = 6;
                        else pipes[r][c] = 4;
                    }
                    else if(pipes[r][c] == 6){
                        if(rotationAngle == 90) pipes[r][c] = 3;
                        else pipes[r][c] = 5;
                    }
                    for(int i = 0; i < SIZE; i++){
                        for(int j = 0; j < SIZE; j++){
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
        if(level == 2){
            root.getChildren().add(moveNum);
        }
        root.getChildren().add(startButton);
        root.setStyle("-fx-background-color: lightblue;");
        Scene scene = new Scene(root, SCENE_SIZE, SCENE_SIZE, Color.LIGHTBLUE);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("Water Pipe");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}