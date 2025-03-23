package br.com.gpf.repository.dao;

import br.com.gpf.repository.DefaultRepositoryException;
import br.com.gpf.repository.model.TransactionTypesModel;

import java.util.List;

public interface TransactionTypesDao {

    void createType(TransactionTypesModel transactionTypesModel) throws DefaultRepositoryException;
    List<TransactionTypesModel> getUserTypes(Integer userId);
    boolean alterType(Integer userId, Integer typeId, String newDesc);
    boolean deleteType(Integer userId, Integer typeId);

}
