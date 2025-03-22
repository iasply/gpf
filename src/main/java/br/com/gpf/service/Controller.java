package br.com.gpf.service;

import br.com.gpf.service.impl.AccountService;
import br.com.gpf.service.impl.AccountServiceImpl;

public class Controller {

    private static Controller instance = null;
    private AccountService accountService;
    private Controller() {
        this.accountService = new  AccountServiceImpl();

    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public AccountService getAccountService() {
        return accountService;
    }
}
