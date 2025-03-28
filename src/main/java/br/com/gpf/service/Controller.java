package br.com.gpf.service;

import br.com.gpf.service.impl.*;

import java.util.Date;

public class Controller {

    private static Controller instance = null;
    private AccountService accountService;
    private TransactionTypeService transactionTypeService;
    private TransactionService transactionService;
    private Session session;
    private Controller() {
        this.session = null;
        this.accountService = new  AccountServiceImpl();
        this.transactionTypeService = new TransactionTypeServiceImpl();
        this.transactionService = new TransactionServiceImpl();

        accountService.createAccount("i","i");
        transactionService.createTransaction(1200.0,1,1,new Date(),"blabla",0);
        transactionService.createTransaction(120.0,0,1,new Date(),"blabla",0);
        transactionService.createTransaction(100.0,1,0,new Date(),"blabla",0);
        transactionService.createTransaction(1200.0,0,0,new Date(),"blabla",0);
        transactionService.createTransaction(1200.0,1,0,new Date(),"blabla",0);
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

    public TransactionService getTransactionService() {
        return transactionService;
    }
}
