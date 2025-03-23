package br.com.gpf.service.impl;

import br.com.gpf.repository.Repository;
import br.com.gpf.repository.model.TransactionTypesModel;
import br.com.gpf.service.DataEnum;
import br.com.gpf.service.RequestStatusEnum;
import br.com.gpf.service.ResponseData;

import java.util.List;

public class TransactionTypeServiceImpl implements TransactionTypeService {

    Repository repository = Repository.getInstance();

    @Override
    public ResponseData createType(Integer userId, String desc) {
        ResponseData responseData = new ResponseData();
        try {
            repository.getTransactionTypesDao().createType(new TransactionTypesModel(desc, userId));
        } catch (Exception e) {
            responseData.setValue(RequestStatusEnum.ERROR);
            responseData.getMapData().put(DataEnum.ERROR_MSG, e.getMessage());
            return responseData;
        }

        responseData.setValue(RequestStatusEnum.SUCCESS);
        return responseData;
    }

    @Override
    public ResponseData getUserTypes(Integer userId) {
        ResponseData responseData = new ResponseData();
        List<TransactionTypesModel> userTypes = repository.getTransactionTypesDao().getUserTypes(userId);
        responseData.setValue(RequestStatusEnum.SUCCESS);
        responseData.getMapData().put(DataEnum.USER_TYPES, userTypes);

        return responseData;
    }

    @Override
    public ResponseData alterType(Integer userId, Integer typeId, String newDesc) {
        ResponseData responseData = new ResponseData();
        boolean alterTypeBoolean = repository.getTransactionTypesDao().alterType(userId, typeId, newDesc);

        if (alterTypeBoolean) {
            responseData.setValue(RequestStatusEnum.SUCCESS);
            return responseData;
        }

        responseData.setValue(RequestStatusEnum.ERROR);
        responseData.getMapData().put(DataEnum.ERROR_MSG, "FALHA AO ALTERAR");

        return responseData;

    }

    @Override
    public ResponseData deleteType(Integer userId, Integer typeId) {
        ResponseData responseData = new ResponseData();

        boolean isDeleted = repository.getTransactionTypesDao().deleteType(userId, typeId);

        if (isDeleted) {
            responseData.setValue(RequestStatusEnum.SUCCESS);
            return responseData;
        }

        responseData.setValue(RequestStatusEnum.ERROR);
        responseData.getMapData().put(DataEnum.ERROR_MSG, "FALHA AO DELETAR");
        return responseData;
    }
}
