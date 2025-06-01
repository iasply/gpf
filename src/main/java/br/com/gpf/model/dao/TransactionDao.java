package br.com.gpf.model.dao;

import br.com.gpf.model.entity.TransactionModel;

import java.util.List;

public interface TransactionDao {
    boolean createTransaction(TransactionModel transactionModel);

    List<TransactionModel> getUserTransaction(Integer userId);

    boolean alterTransactionType(Integer userId, Integer oldTypeId, Integer newTypeId);

}
