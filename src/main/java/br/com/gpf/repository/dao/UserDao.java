package br.com.gpf.repository.dao;

import br.com.gpf.repository.model.UserModel;

public interface UserDao {
    boolean isValidUser(String userName, String password);

    boolean createUser(String userName, String password);

    UserModel getUserByName(String userName);
}
