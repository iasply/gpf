package br.com.gpf.model.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "transactions")
public class TransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(nullable = false)
    private Double value;
    @Column(name = "transaction_classification", nullable = false)
    private Integer transactionClassification;

    @ManyToOne
    @JoinColumn(name = "transaction_type_id", nullable = false)
    private TransactionTypesModel transactionType;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;
    @Column(name = "description_text")
    private String descriptionText;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    public TransactionModel() {
    }

    public TransactionModel(Double value, Integer transactionClassification, TransactionTypesModel transactionType, Date date, String descriptionText, UserModel user) {
        this.value = value;
        this.transactionClassification = transactionClassification;
        this.transactionType = transactionType;
        this.date = date;
        this.descriptionText = descriptionText;
        this.user = user;
    }

    public TransactionModel(Integer id, Double value, Integer transactionClassification, TransactionTypesModel transactionType, Date date, String descriptionText, UserModel user) {
        this.id = id;
        this.value = value;
        this.transactionClassification = transactionClassification;
        this.transactionType = transactionType;
        this.date = date;
        this.descriptionText = descriptionText;
        this.user = user;
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

    public TransactionTypesModel getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypesModel transactionType) {
        this.transactionType = transactionType;
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

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}


