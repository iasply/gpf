package br.com.gpf.controller.impl;

import br.com.gpf.model.Repository;
import br.com.gpf.model.entity.TransactionModel;
import br.com.gpf.model.entity.TransactionTypesModel;
import br.com.gpf.model.entity.UserModel;
import br.com.gpf.controller.DataEnum;
import br.com.gpf.controller.RequestStatusEnum;
import br.com.gpf.controller.ResponseData;
import br.com.gpf.controller.service.TransactionService;

import java.util.Date;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    private final Repository repository = Repository.getInstance();

    @Override
    public ResponseData createTransaction(Double value, Integer transactionClassification, Integer transactionTypeId, Date transactionDate, String descriptionText, Integer userId) {
        ResponseData responseData = new ResponseData();
        UserModel userById = repository.getUserDao().getUserById(userId);
        TransactionTypesModel typeById = repository.getTransactionTypesDao().getTypeById(transactionTypeId);

        try {
            TransactionModel transaction = new TransactionModel();

            transaction.setUser(userById);
            transaction.setValue(value);
            transaction.setTransactionType(typeById);
            transaction.setTransactionClassification(transactionClassification);
            transaction.setDate(transactionDate);
            transaction.setDescriptionText(descriptionText);

            boolean success = repository.getTransactionDao().createTransaction(transaction);

            if (!success) {
                responseData.setValue(RequestStatusEnum.ERROR);
                responseData.getMapData().put(DataEnum.ERROR_MSG, "Falha ao criar transação.");
                return responseData;
            }

            responseData.setValue(RequestStatusEnum.SUCCESS);
        } catch (Exception e) {
            responseData.setValue(RequestStatusEnum.ERROR);
            responseData.getMapData().put(DataEnum.ERROR_MSG, e.getMessage());
        }

        return responseData;
    }

    @Override
    public ResponseData getUserTransaction(Integer userId) {
        ResponseData responseData = new ResponseData();

        try {
            List<TransactionModel> transactions = repository.getTransactionDao().getUserTransaction(userId);
            responseData.setValue(RequestStatusEnum.SUCCESS);
            responseData.getMapData().put(DataEnum.USER_TRANSACTIONS, transactions);
        } catch (Exception e) {
            responseData.setValue(RequestStatusEnum.ERROR);
            responseData.getMapData().put(DataEnum.ERROR_MSG, e.getMessage());
        }

        return responseData;
    }

    @Override
    public ResponseData alterTransactionType(Integer userId, Integer oldTypeId, Integer newTypeId) {
        ResponseData responseData = new ResponseData();

        try {
            boolean isUpdated = repository.getTransactionDao().alterTransactionType(userId, oldTypeId, newTypeId);

            if (!isUpdated) {
                responseData.setValue(RequestStatusEnum.ERROR);
                responseData.getMapData().put(DataEnum.ERROR_MSG, "Falha ao atualizar o tipo de transação.");
                return responseData;
            }

            responseData.setValue(RequestStatusEnum.SUCCESS);
        } catch (Exception e) {
            responseData.setValue(RequestStatusEnum.ERROR);
            responseData.getMapData().put(DataEnum.ERROR_MSG, e.getMessage());
        }

        return responseData;
    }
}
