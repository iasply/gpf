package br.com.gpf.repository.dao;

import br.com.gpf.repository.dao.UserDao;
import br.com.gpf.repository.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private int id = 0;
    private final List<UserModel> list;

    public UserDaoImpl() {
        this.list = new ArrayList<>();
    }

    @Override
    public boolean isValidUser(String userName, String password) {
        return list.stream().anyMatch(userModel -> userModel.getUserName().equals(userName) && userModel.getPassword().equals(password));
    }

    @Override
    public boolean createUser(String userName, String password) {
        list.add(new UserModel(id, userName, password));
        id++;
        return true;
    }

    @Override
    public UserModel getUserByName(String userName) {
        return list.stream()
                .filter(i -> i.getUserName().equals(userName))
                .findFirst()
                .orElse(null);
    }


}
