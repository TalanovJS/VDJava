package vdcom.base;

public class Content {
    private Double leftDouble;
    private Value leftValue;
    private Double rightDouble;
    private Value rightValue;

    public Content(Double leftDouble, Value leftValue, Double rightDouble, Value rightValue) {
        this.leftDouble = leftDouble;
        this.leftValue = leftValue;
        this.rightDouble = rightDouble;
        this.rightValue = rightValue;
    }

    public Double getLeftDouble() {
        return leftDouble;
    }

    public void setLeftDouble(Double leftDouble) {
        this.leftDouble = leftDouble;
    }

    public Value getLeftValue() {
        return leftValue;
    }

    public void setLeftValue(Value leftValue) {
        this.leftValue = leftValue;
    }

    public Double getRightDouble() {
        return rightDouble;
    }

    public void setRightDouble(Double rightDouble) {
        this.rightDouble = rightDouble;
    }

    public Value getRightValue() {
        return rightValue;
    }

    public void setRightValue(Value rightValue) {
        this.rightValue = rightValue;
    }
}
