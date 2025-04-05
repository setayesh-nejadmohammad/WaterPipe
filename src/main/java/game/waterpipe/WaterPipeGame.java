package game.waterpipe;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class WaterPipeGame extends Application {
        private static final int PIPE_X = 100; // مختصات x لوله
        private static final int PIPE_Y = 50;  // مختصات y لوله
        private static final int PIPE_WIDTH = 20;
        private static final int PIPE_HEIGHT = 200;
        private double waterLevel = -20; // سطح آب، به طور پیش‌فرض خارج از لوله است

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Water Pipe Game");

        Canvas canvas = new Canvas(400, 300);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        drawPipe(gc);
        drawWater(gc);

        // انیمیشن حرکت آب
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // به آرامی سطح آب را افزایش دهید تا در لوله حرکت کند
                if (waterLevel < PIPE_HEIGHT) {
                    waterLevel += 1; // سرعت حرکت آب
                }
                draw(gc);
            }
        };
        timer.start();

        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }

    private void drawPipe(GraphicsContext gc) {
        gc.setFill(Color.GRAY);
        gc.fillRect(PIPE_X, PIPE_Y, PIPE_WIDTH, PIPE_HEIGHT);
    }

    private void drawWater(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillRect(PIPE_X, PIPE_Y + PIPE_HEIGHT - waterLevel, PIPE_WIDTH, waterLevel);
    }

    private void draw(GraphicsContext gc) {
        // پاک‌سازی canvas
        gc.clearRect(0, 0, 400, 300);
        drawPipe(gc);
        drawWater(gc);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

