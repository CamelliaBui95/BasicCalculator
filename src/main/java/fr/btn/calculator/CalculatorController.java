package fr.btn.calculator;

import fr.btn.calculator.Calculator;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CalculatorController {
    @FXML
    private Label display;
    private Calculator calculator;

    @FXML
    private void initialize() {
        this.calculator = new Calculator();
        display.setText(calculator.toString());
    }

    public void handleOnButtonClick(Event event) {
        Button button = (Button) event.getSource();
        calculator.touch(button.getText());
        display.setText(calculator.toString());
    }
}

// ctrl + alt + l
