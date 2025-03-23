package br.com.gpf.service;

import br.com.gpf.service.impl.AccountService;
import br.com.gpf.service.impl.AccountServiceImpl;
import br.com.gpf.service.impl.TransactionTypeService;
import br.com.gpf.service.impl.TransactionTypeServiceImpl;

public class Controller {

    private static Controller instance = null;
    private AccountService accountService;
    private TransactionTypeService transactionTypeService;
    private Session session;
    private Controller() {
        this.session = null;
        this.accountService = new  AccountServiceImpl();
        this.transactionTypeService = new TransactionTypeServiceImpl();

        accountService.createAccount("i","i");
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

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public TransactionTypeService getTransactionTypeService() {
        return transactionTypeService;
    }
}
