package game.waterpipe;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class WaterPipeGame2 extends Application {
    private static final int GRID_SIZE = 6; // اندازه گرید
    private static final int CELL_SIZE = 50; // اندازه هر سلول
    private static final int PIPE_WIDTH = 10; // عرض لوله
    private int waterLevel = 0; // سطح آب

    // آرایه نشان دهنده مسیر لوله‌ها (1: لوله، 0: خالی)
    private int[][] pipeLayout = {
            {1, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 0, 0},
            {0, 0, 0, 1, 0, 0},
            {0, 0, 0, 1, 0, 0},
            {0, 0, 0, 1, 0, 0}
    };

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Water Pipe Game");

        Canvas canvas = new Canvas(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // انیمیشن حرکت آب
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw(gc);
                waterLevel++;
                if (waterLevel > GRID_SIZE * CELL_SIZE) {
                    waterLevel = 0; // ریست سطح آب
                }
            }
        };
        timer.start();

        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root, GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE));
        primaryStage.show();
    }

    private void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE);

        // رسم لوله‌ها و آب
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (pipeLayout[i][j] == 1) {
                    // رسم لوله
                    gc.setFill(Color.GRAY);
                    gc.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                    // رسم آب
                    gc.setFill(Color.BLUE);
                    // اگر سطح آب از ابتدای لوله پایین‌تر باشد، آبی رسم نکن
                    if (waterLevel > i * CELL_SIZE && waterLevel < (i + 1) * CELL_SIZE) {
                        gc.fillRect(j * CELL_SIZE, waterLevel, CELL_SIZE, CELL_SIZE);
                    } else if (waterLevel >= (i + 1) * CELL_SIZE) {
                        gc.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

