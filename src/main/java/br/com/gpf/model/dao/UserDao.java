package br.com.gpf.model.dao;

import br.com.gpf.model.entity.UserModel;

public interface UserDao {
    boolean isValidUser(String userName, String password);

    boolean createUser(String userName, String password);

    UserModel getUserByName(String userName);

    UserModel getUserById(Integer id);
}
