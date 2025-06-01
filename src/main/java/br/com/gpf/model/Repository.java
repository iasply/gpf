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

    private Repository() {


    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }


}
