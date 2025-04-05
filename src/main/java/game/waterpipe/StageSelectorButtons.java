package game.waterpipe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StageSelectorButtons extends Application {

    private Stage primaryStage; // پنجره اصلی برنامه

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage; // ذخیره پنجره اصلی

        Stage stageSelector = new Stage(); // ایجاد پنجره انتخاب مرحله
        stageSelector.setTitle("انتخاب مرحله"); // تعیین عنوان پنجره

        Label label = new Label("لطفاً مرحله مورد نظر را انتخاب کنید:"); // ایجاد برچسب

        // ایجاد دکمه‌ها برای انتخاب مرحله
        Button button1 = new Button("مرحله 1");
        Button button2 = new Button("مرحله 2");

        // تنظیم عملکرد دکمه‌ها
        button1.setOnAction(e -> {
            startGame(1); // شروع بازی با مرحله 1
            stageSelector.close(); // بستن پنجره انتخاب مرحله
        });

        button2.setOnAction(e -> {
            startGame(2); // شروع بازی با مرحله 2
            stageSelector.close(); // بستن پنجره انتخاب مرحله
        });

        // طراحی چیدمان دکمه‌ها به صورت افقی
        HBox buttonsLayout = new HBox(10);
        buttonsLayout.setAlignment(Pos.CENTER);
        buttonsLayout.getChildren().addAll(button1, button2);

        // طراحی چیدمان کلی عناصر به صورت عمودی
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, buttonsLayout);

        Scene scene = new Scene(layout, 400, 200); // ایجاد صحنه
        stageSelector.setScene(scene); // تنظیم صحنه
        stageSelector.show(); // نمایش پنجره انتخاب مرحله
    }

    // تابع شروع بازی با مرحله انتخاب شده
    private void startGame(int level) {
        // ایجاد صحنه اصلی بازی با مرحله انتخاب شده
        // در اینجا می توانید منطق بازی خود را پیاده سازی کنید
        // این فقط یک نمونه است
        Label gameLabel = new Label("بازی در مرحله " + level + " شروع شد!");
        VBox gameLayout = new VBox(10);
        gameLayout.setAlignment(Pos.CENTER);
        gameLayout.getChildren().add(gameLabel);

        Scene gameScene = new Scene(gameLayout, 600, 400);
        primaryStage.setTitle("صفحه اصلی بازی");
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

