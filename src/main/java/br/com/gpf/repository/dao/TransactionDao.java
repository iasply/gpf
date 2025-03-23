package br.com.gpf.repository.dao;

import br.com.gpf.repository.model.TransactionModel;

import java.util.List;

public interface TransactionDao  {
    boolean createTransaction(TransactionModel transactionModel);
    List<TransactionModel> getUserTransaction(Integer userId);
    boolean alterTransactionType(Integer userId,Integer oldTypeId,Integer newTypeId);

}
