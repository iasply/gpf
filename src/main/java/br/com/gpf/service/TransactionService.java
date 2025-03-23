package br.com.gpf.service;

import java.util.Date;

public interface TransactionService {
    ResponseData createTransaction(Double value, Integer transactionClassification, Integer transactionType, Date transactionDate, String descriptionText, Integer userId);

    ResponseData getUserTransaction(Integer userId);

    ResponseData alterTransactionType(Integer userId, Integer oldTypeId, Integer newTypeId);

}

