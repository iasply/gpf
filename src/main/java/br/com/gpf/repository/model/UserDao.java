package br.com.gpf.repository.model;

public interface UserDao {
    Boolean isValidUser(String userName,String password);
    Boolean createUser(String userName,String password);
}
