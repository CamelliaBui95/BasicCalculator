package fr.btn.calculator;


import javafx.scene.control.Button;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calculator {
    private Operand leftOperand;
    private Operand rightOperand;
    private List<String> digits = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    private List<String> operators = Arrays.asList("+", "-", "X", "/");
    private String operator = "";
    private String operatorInMemory = "";
    private String memory = "";
    private int pointCount = 0;
    private boolean hasCalculated = false;

    public Calculator() {
        leftOperand = new Operand("0");
        rightOperand = new Operand("");
    }
    public void touch(String code) {
        switch(code) {
            case "=" -> calculate();
            case "C" -> reset();
            case "M+"-> memorise();
            case "CM" -> clearMemory();
            case "M" -> getMemory();
            case "+/-" -> negate();
            default -> defaultTouch(code);
        }
    }

    private void defaultTouch(String code) {
        if(hasCalculated) {
            if(operator.isEmpty() && !isOperator(code))
                reset();
            else hasCalculated = false;
        }

        if(!rightOperand.isEmpty() && isOperator(code))
            calculate();

        if(digits.contains(code))
            addDigit(code);
        else if(operators.contains(code))
            operator = code;
        else
            addPoint(code);
    }
    private void addDigit(String digit) {
        if(operator.isEmpty())
            leftOperand.addDigit(digit);
        else rightOperand.addDigit(digit);
    }

    private void addPoint(String point) {
        if(pointCount == 1 && operator.isEmpty())
            return;

        if(pointCount == 2)
            return;

        if(operator.isEmpty())
            leftOperand.addPoint(point);
        else rightOperand.addPoint(point);

        pointCount++;
    }

    private void calculate() {
        if(operator.isEmpty()) {
            if(!leftOperand.isEmpty() && !operatorInMemory.isEmpty())
                operator = operatorInMemory;
            else return;
        }

        switch(operator) {
            case "+" -> addOperation();
            case "-" -> subtractOperation();
            case "/" -> divideOperation();
            default -> multiplyOperation();
        }
    }

    private void divideOperation() {
        if(rightOperand.isEmpty())
            leftOperand.divideOther(leftOperand.bigDecimal);
        else
            leftOperand.divideOther(rightOperand.bigDecimal);

        hasCalculated = true;
        clearRightOperand();
    }

    private void multiplyOperation() {
        if(rightOperand.isEmpty())
            leftOperand.multiplyOther(leftOperand.bigDecimal);
        else
            leftOperand.multiplyOther(rightOperand.bigDecimal);

        hasCalculated = true;
        clearRightOperand();
    }

    private void addOperation() {
        if(rightOperand.isEmpty())
            leftOperand.addOther(leftOperand.bigDecimal);
        else
            leftOperand.addOther(rightOperand.bigDecimal);

        hasCalculated = true;
        clearRightOperand();
    }

    private void clearRightOperand() {
        operatorInMemory = operator;
        operator = "";
        rightOperand.clear();
    }

    private void subtractOperation() {
        if(rightOperand.isEmpty())
            leftOperand.subtractOther(leftOperand.bigDecimal);
        else
            leftOperand.subtractOther(rightOperand.bigDecimal);

        hasCalculated = true;
        clearRightOperand();
    }

    private void memorise() {
        if(!operator.isEmpty())
            calculate();

        memory = leftOperand.getLiteral();
        hasCalculated = true;
    }

    private void getMemory() {
        if(memory.isEmpty())
            return;

        if(operator.isEmpty())
            leftOperand.init(memory);
        else rightOperand.init(memory);

    }
    private void clearMemory() {
        memory = "";
    }
    private void reset() {
        operator = "";
        operatorInMemory = "";
        leftOperand.init();
        rightOperand.clear();
        hasCalculated = false;
    }

    private void negate() {
        if(!rightOperand.isEmpty())
            rightOperand.negate();
        else
            leftOperand.negate();
    }

    private boolean isOperator(String code) {
        return operators.contains(code);
    }
    @Override
    public String toString() {
        return leftOperand.getLiteral() + " " + operator + " " + rightOperand.getLiteral();
    }

}
