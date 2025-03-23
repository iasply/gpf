package br.com.gpf.service.impl;

import br.com.gpf.service.ResponseData;

public interface AccountService {

    ResponseData login(String userName, String password);

    ResponseData createAccount(String userName, String password);
}
