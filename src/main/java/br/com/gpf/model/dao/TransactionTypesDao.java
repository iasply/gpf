package br.com.gpf.model.dao;

import br.com.gpf.model.DefaultRepositoryException;
import br.com.gpf.model.entity.TransactionTypesModel;

import java.util.List;

public interface TransactionTypesDao {

    void createType(TransactionTypesModel transactionTypesModel) throws DefaultRepositoryException;

    List<TransactionTypesModel> getUserTypes(Integer userId);

    boolean alterType(Integer userId, Integer typeId, String newDesc);

    boolean deleteType(Integer userId, Integer typeId);

    TransactionTypesModel getTypeById(Integer id);

}
