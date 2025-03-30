package br.com.gpf.service;

import br.com.gpf.service.impl.*;

import java.util.Date;

public class Controller {

    private static Controller instance = null;
    private final AccountService accountService;
    private final TransactionTypeService transactionTypeService;
    private final TransactionService transactionService;
    private Session session;


    private void test() {
        // Criando contas para o usuário 0 e 1
        accountService.createAccount("i", "i");

    }

    private Controller() {
        this.session = null;
        this.accountService = new AccountServiceImpl();
        this.transactionTypeService = new TransactionTypeServiceImpl();
        this.transactionService = new TransactionServiceImpl();

        test();
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
