package br.com.gpf.service.impl;

import br.com.gpf.repository.Repository;
import br.com.gpf.repository.model.TransactionTypesModel;
import br.com.gpf.repository.model.UserModel;
import br.com.gpf.service.*;

public class AccountServiceImpl implements AccountService {

    Repository repository = Repository.getInstance();

    @Override
    public ResponseData login(String userName, String password) {

        boolean validUser = repository.getUserDao().isValidUser(userName, password);
        ResponseData responseData = new ResponseData();
        if (validUser) {
            UserModel userByName = repository.getUserDao().getUserByName(userName);
            Controller.getInstance().setSession(new Session(userByName.getId(), userByName.getUserName()));
            responseData.setValue(RequestStatusEnum.SUCCESS);
            return responseData;
        }

        responseData.setValue(RequestStatusEnum.ERROR);
        responseData.getMapData().put(DataEnum.ERROR_MSG, "FALHA NO LOGIN, VERIFIQUE AS CREDENCIAIS");
        return responseData;

    }

    @Override
    public ResponseData createAccount(String userName, String password) {

        ResponseData responseData = new ResponseData();

        if(repository.getUserDao().getUserByName(userName) != null) {
            responseData.setValue(RequestStatusEnum.ERROR);
            responseData.getMapData().put(DataEnum.ERROR_MSG, "JÁ EXISTE UM USUARIO COM ESSE NOME");
            return responseData;
        }

        boolean userCreated = repository.getUserDao().createUser(userName, password);

        if (userCreated) {
            UserModel userByName = repository.getUserDao().getUserByName(userName);

            repository.getTransactionTypesDao().createType(new TransactionTypesModel("Salario", userByName.getId()));
            repository.getTransactionTypesDao().createType(new TransactionTypesModel("Cartão", userByName.getId()));

            responseData.setValue(RequestStatusEnum.SUCCESS);
            return responseData;
        }

        responseData.setValue(RequestStatusEnum.ERROR);
        responseData.getMapData().put(DataEnum.ERROR_MSG, "FALHA AO CRIAR O USUARIO");
        return responseData;
    }
}
