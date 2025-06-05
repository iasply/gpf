package br.com.gpf.controller;

import br.com.gpf.model.dao.TransactionDao;
import br.com.gpf.model.dao.TransactionTypesDao;
import br.com.gpf.model.dao.UserDao;
import br.com.gpf.model.impl.TransactionDaoImpl;
import br.com.gpf.model.impl.TransactionTypesDaoImpl;
import br.com.gpf.model.impl.UserDaoImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ServiceLocator {

    private static ServiceLocator instance = null;
    private Session session;
    private UserDao userDao;
    private TransactionTypesDao transactionTypesDao;
    private TransactionDao transactionDao;


    private ServiceLocator() {
        controllerInit();
        modelInit();
    }

    private void modelInit() {
        Configuration configuration = new Configuration();
        configuration.configure();


        SessionFactory factory = configuration.buildSessionFactory();
        org.hibernate.Session session = factory.openSession();
        this.userDao = new UserDaoImpl(session);
        this.transactionTypesDao = new TransactionTypesDaoImpl(session);
        this.transactionDao = new TransactionDaoImpl(session);
    }

    private void controllerInit() {
        this.session = null;

    }

    public static ServiceLocator getInstance() {
        if (instance == null) {
            instance = new ServiceLocator();
        }
        return instance;
    }


    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
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
