package br.com.gpf.repository;

import br.com.gpf.repository.dao.TransactionTypesDao;
import br.com.gpf.repository.dao.TransactionTypesDaoImpl;
import br.com.gpf.repository.dao.UserDao;
import br.com.gpf.repository.dao.UserDaoImpl;

public class Repository {
    private  static Repository instance;
    private final UserDao userDao;
    private final TransactionTypesDao transactionTypesDao;
    private Repository() {
        userDao =new UserDaoImpl();
        transactionTypesDao = new TransactionTypesDaoImpl();
    }

    public static Repository getInstance(){
        if(instance == null){
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
}
