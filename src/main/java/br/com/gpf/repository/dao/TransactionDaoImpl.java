package br.com.gpf.repository.dao;

import br.com.gpf.repository.model.TransactionModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionDaoImpl implements TransactionDao {

    private final List<TransactionModel> list;
    private int id = 0;

    public TransactionDaoImpl() {
        this.list = new ArrayList<>();
    }

    @Override
    public boolean createTransaction(TransactionModel transactionModel) {
        if (transactionModel != null) {
            transactionModel.setId(id);
            id++;
            list.add(transactionModel);
            return true;
        }
        return false;
    }

    @Override
    public List<TransactionModel> getUserTransaction(Integer userId) {
        return list.stream()
                .filter(transaction -> transaction.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public boolean alterTransactionType(Integer userId, Integer oldTypeId, Integer newTypeId) {
        list.stream()
                .filter(transaction -> transaction.getUserId().equals(userId) && transaction.getTransactionTypeId().equals(oldTypeId))
                .forEach(transaction -> transaction.setTransactionTypeId(newTypeId));

        return true;
    }

}
