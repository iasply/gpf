package br.com.gpf.controller.service;

import br.com.gpf.controller.ResponseData;

public interface AccountService {

    ResponseData login(String userName, String password);

    ResponseData createAccount(String userName, String password);
}
