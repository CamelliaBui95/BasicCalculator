package fr.btn.calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class CalculatorApplication extends Application {
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(CalculatorApplication.class.getResource("calculator-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 383,524);
        stage.setTitle("Calculator");
        scene.getStylesheets().add(getClass().getResource("calc.css").toExternalForm());
        stage.setScene(scene);
        stage.setMinHeight(550);
        stage.setMinWidth(400);
        stage.setMaxHeight(550);
        stage.setMaxWidth(400);
        stage.show();
    }
}

