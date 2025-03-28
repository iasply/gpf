package br.com.gpf.repository;

import br.com.gpf.repository.dao.*;

public class Repository {
    private static Repository instance;
    private final UserDao userDao;
    private final TransactionTypesDao transactionTypesDao;
    private final TransactionDao transactionDao;

    private Repository() {
        this.userDao = new UserDaoImpl();
        this.transactionTypesDao = new TransactionTypesDaoImpl();
        this.transactionDao = new TransactionDaoImpl();
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public TransactionTypesDao getTransactionTypesDao() {
        return transactionTypesDao;
    }

    public TransactionDao getTransactionDao() {
        return transactionDao;
    }
}
