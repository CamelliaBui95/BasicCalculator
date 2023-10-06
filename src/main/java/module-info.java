module fr.btn.calculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens fr.btn.calculator to javafx.fxml;
    exports fr.btn.calculator;
}