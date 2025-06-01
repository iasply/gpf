package br.com.gpf.model;

import br.com.gpf.model.dao.*;
import br.com.gpf.model.impl.TransactionDaoImpl;
import br.com.gpf.model.impl.TransactionTypesDaoImpl;
import br.com.gpf.model.impl.UserDaoImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Repository {
    private static Repository instance;
    private UserDao userDao;
    private TransactionTypesDao transactionTypesDao;
    private TransactionDao transactionDao;

    private Repository() {
        Configuration configuration = new Configuration();
        configuration.configure(); // lê hibernate.cfg.xml


        SessionFactory factory = configuration.buildSessionFactory();
        Session session = factory.openSession();
            this.userDao = new UserDaoImpl(session);
            this.transactionTypesDao = new TransactionTypesDaoImpl(session);
            this.transactionDao = new TransactionDaoImpl(session);

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
