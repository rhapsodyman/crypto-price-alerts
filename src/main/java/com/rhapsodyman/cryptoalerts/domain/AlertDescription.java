package com.rhapsodyman.cryptoalerts.domain;

public class AlertDescription {

    private String symbol;
    private PriceOperator operator;
    private String targetValue;

    public AlertDescription() {

    }

    public AlertDescription(String symbol, PriceOperator operator, String targetValue) {
        this.symbol = symbol;
        this.operator = operator;
        this.targetValue = targetValue;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public PriceOperator getOperator() {
        return operator;
    }

    public void setOperator(PriceOperator operator) {
        this.operator = operator;
    }

    public String getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }

}
