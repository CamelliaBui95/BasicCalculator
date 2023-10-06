package fr.btn.calculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class Operand {
    private static final int PRECISION = 65;
    private static final int NB_DECIMAL = 10;
    NumberFormat numberFormat = NumberFormat.getInstance(Locale.FRENCH);
    private StringBuilder literal;
    public BigDecimal bigDecimal = new BigDecimal(0);

    private String prevVal = "";
    public Operand(String val) {
        literal = new StringBuilder();
        numberFormat.setMaximumFractionDigits(NB_DECIMAL);
        literal.append(val);
    }

    public void init() {
        if(literal == null)
            literal = new StringBuilder();

        clear();
        literal.append("0");
    }

    public void init(String initialVal) {
        literal.delete(0, literal.length());
        literal.append(initialVal);
        myBigDecimalFormat();
    }

    public void clear() {
        literal.delete(0, literal.length());
        bigDecimal = new BigDecimal("0");
    }

    public void addDigit(String digit) {
        if(literal.length() == 1 && literal.charAt(0) == '0')
            literal.delete(0, 1);

        literal.append(digit);
        myBigDecimalFormat();
    }

    public void addPoint(String point) {
        if(isEmpty()) {
            literal.append("0").append(",");
            return;
        }
        literal.append(point);
        myBigDecimalFormat();
    }

    public void addOther(BigDecimal other) {
        bigDecimal = bigDecimal.add(other);
        formatNumericalLiteral();
    }

    public void subtractOther(BigDecimal other) {
        System.out.println("this=" + bigDecimal);
        System.out.println("other=" + other);
        bigDecimal = bigDecimal.subtract(other);
        formatNumericalLiteral();
    }

    public void multiplyOther(BigDecimal other) {
        bigDecimal = bigDecimal.multiply(other);
        formatNumericalLiteral();
    }

    public void divideOther(BigDecimal other) {
        try {
            bigDecimal = bigDecimal.divide(other, new MathContext(PRECISION, RoundingMode.HALF_UP));
            formatNumericalLiteral();
        } catch(Exception e) {
            setError();
        }
    }

    public void negate() {
        if(literal.charAt(0) == '0')
            return;

        if(literal.charAt(0) == '-')
            literal.deleteCharAt(0);
        else
            literal.insert(0, '-');

        myBigDecimalFormat();
    }

    private void myBigDecimalFormat() {
        try {
            if(literal.isEmpty()) bigDecimal = new BigDecimal("0");
            else {
                String bigDecStr = literal.toString().replace(",", ".");
                bigDecimal = new BigDecimal(numberFormat.parse(bigDecStr).doubleValue());
            }
        } catch(Exception e) {
            literal.delete(0, literal.length());
            literal.append("Error");
        }
    }
    private void formatNumericalLiteral() {
        literal.delete(0, literal.length());
        literal.append(numberFormat.format(bigDecimal));
    }

    public String getLiteral() {
        return literal.toString();
    }

    public boolean isEmpty() {
        return literal.isEmpty();
    }

    private void setError() {
        literal.delete(0, literal.length());
        literal.append("Error");
    }
}
