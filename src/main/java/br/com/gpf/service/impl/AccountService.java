package br.com.gpf.service.impl;

import br.com.gpf.service.DataEnum;
import br.com.gpf.service.ResponseData;

import java.util.Map;

public interface AccountService {

    ResponseData login(String userName, String password);
    ResponseData  createAccount(String userName,String password);
}
