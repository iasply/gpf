package br.com.gpf.repository;

import br.com.gpf.repository.model.UserDao;
import br.com.gpf.repository.model.UserDaoImpl;

public class Repository {
    private  static Repository instance;
    private final UserDao userDao;

    private Repository() {
        userDao =new UserDaoImpl();
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
}
