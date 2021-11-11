package com.rhapsodyman.cryptoalerts.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "alerts")
public class TriggerableAlert {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "symbol")
    private String symbol;

    @Enumerated(EnumType.STRING)
    @Column(name = "operator")
    private PriceOperator operator;

    @Column(name = "targetValue")
    private String targetValue;

    @Column(name = "triggeredValue")
    private String triggeredValue;

    @Column(name = "wasTriggered")
    private boolean wasTriggered = false;

    public TriggerableAlert() {

    }


    @JsonIgnore
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User owner;

    public TriggerableAlert(String symbol, PriceOperator operator, String targetValue) {
        this.symbol = symbol;
        this.operator = operator;
        this.targetValue = targetValue;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean isWasTriggered() {
        return wasTriggered;
    }

    public void setWasTriggered(boolean wasTriggered) {
        this.wasTriggered = wasTriggered;
    }

    @Override
    public String toString() {
        return "TriggerableAlert{" +
                "symbol='" + symbol + '\'' +
                ", operator=" + operator +
                ", triggeredValue='" + triggeredValue + '\'' +
                '}';
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getTriggeredValue() {
        return triggeredValue;
    }

    public void setTriggeredValue(String triggeredValue) {
        this.triggeredValue = triggeredValue;
    }
}