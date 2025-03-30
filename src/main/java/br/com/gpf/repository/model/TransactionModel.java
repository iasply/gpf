package br.com.gpf.repository.model;

import java.util.Date;

public class TransactionModel {
    private Integer id;
    private Double value;
    private Integer transactionClassification;
    private Integer transactionTypeId;
    private Date date;
    private String descriptionText;
    private Integer userId;

    public TransactionModel() {
    }

    public TransactionModel(Double value, Integer transactionClassification, Integer transactionTypeId, Date date, String descriptionText, Integer userId) {
        this.value = value;
        this.transactionClassification = transactionClassification;
        this.transactionTypeId = transactionTypeId;
        this.date = date;
        this.descriptionText = descriptionText;
        this.userId = userId;
    }

    public TransactionModel(Integer id, Double value, Integer transactionClassification, Integer transactionTypeId, Date date, String descriptionText, Integer userId) {
        this.id = id;
        this.value = value;
        this.transactionClassification = transactionClassification;
        this.transactionTypeId = transactionTypeId;
        this.date = date;
        this.descriptionText = descriptionText;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getTransactionClassification() {
        return transactionClassification;
    }

    public void setTransactionClassification(Integer transactionClassification) {
        this.transactionClassification = transactionClassification;
    }

    public Integer getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Integer transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
