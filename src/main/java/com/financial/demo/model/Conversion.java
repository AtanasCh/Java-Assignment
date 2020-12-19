package com.financial.demo.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transaction")
public class Conversion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "transaction_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)//HH:mm dd-MM-yyyy
    private Date transactionDate = new Date(); // possible mistake

    @Column(name = "exchange_rate")
    private Double exchangeRate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "source_currency")
    private CurrencyType sourceCurrency;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "target_currency")
    private CurrencyType targetCurrency;

    @Column(name = "source_amount")
    private Double sourceAmount;

    @Column(name = "target_amount")
    private Double targetAmount;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "transaction_status")
    private StatusType transactionStatus = StatusType.PENDING;

    public Conversion(){}

    public Conversion(Date transactionDate, Double exchangeRate, CurrencyType sourceCurrency,
                      CurrencyType targetCurrency, Double sourceAmount, Double targetAmount, StatusType transactionStatus) {
        this.transactionDate = transactionDate;
        this.exchangeRate = exchangeRate;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.sourceAmount = sourceAmount;
        this.targetAmount = targetAmount;
        this.transactionStatus = transactionStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public CurrencyType getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(CurrencyType sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public CurrencyType getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(CurrencyType targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public Double getSourceAmount() {
        return sourceAmount;
    }

    public void setSourceAmount(Double sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    public Double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(Double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public StatusType getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(StatusType transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
