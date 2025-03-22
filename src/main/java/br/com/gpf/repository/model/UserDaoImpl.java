package br.com.gpf.repository.model;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private final List<UserModel> list;

    public UserDaoImpl() {
        this.list =new  ArrayList<>();
    }

    @Override
    public Boolean isValidUser(String userName, String password) {
        return list.stream().anyMatch(userModel -> userModel.getUserName().equals(userName) && userModel.getPassword().equals(password));
    }

    public Boolean createUser(String userName, String password){
        list.add(new UserModel(0,userName,password));
        return true;
    }
}
