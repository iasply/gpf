package br.com.gpf.service.impl;

import br.com.gpf.repository.Repository;
import br.com.gpf.service.DataEnum;
import br.com.gpf.service.RequestStatusEnum;
import br.com.gpf.service.ResponseData;

public class AccountServiceImpl implements AccountService {

     Repository repository= Repository.getInstance();
    @Override
    public ResponseData login(String userName, String password) {
        Boolean validUser =repository.getUserDao().isValidUser(userName, password);
        ResponseData responseData = new ResponseData();
        if (validUser){
            responseData.setValue(RequestStatusEnum.SUCESS);
            return responseData;
        }

        responseData.setValue(RequestStatusEnum.ERROR);
        responseData.getMapData().put(DataEnum.ERROR_MSG,"FALHA NO LOGIN, VERIFIQUE AS CREDENCIAIS");
        return responseData;

    }

    @Override
    public ResponseData createAccount(String userName, String password) {
        Boolean userCreted = repository.getUserDao().createUser(userName, password);
        ResponseData responseData = new ResponseData();

        if (userCreted) {
            responseData.setValue(RequestStatusEnum.SUCESS);
            return responseData;
        }

        responseData.setValue(RequestStatusEnum.ERROR);
        responseData.getMapData().put(DataEnum.ERROR_MSG,"FALHA AO CRIAR O USUARIO");
        return responseData;
    }
}
