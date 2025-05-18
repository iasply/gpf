package br.com.gpf.repository.dao;

import br.com.gpf.repository.model.UserModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class UserDaoImpl implements UserDao {

    private final EntityManager entityManager;

    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean isValidUser(String userName, String password) {
        TypedQuery<Long> query = entityManager.createQuery(
                "select count(u) from UserModel u where u.userName = :username and u.password = :password", Long.class);
        query.setParameter("username", userName);
        query.setParameter("password", password);

        return query.getSingleResult() > 0;
    }

    @Override
    public boolean createUser(String userName, String password) {
        try {
            Long count = entityManager.createQuery(
                            "select count(u) from UserModel u where u.userName = :userName", Long.class)
                    .setParameter("userName", userName)
                    .getSingleResult();

            if (count > 0) {
                return false;
            }

            entityManager.getTransaction().begin();

            UserModel user = new UserModel();
            user.setUserName(userName);
            user.setPassword(password);

            entityManager.persist(user);
            entityManager.getTransaction().commit();

            return true;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public UserModel getUserByName(String userName) {
        try {
            return entityManager.createQuery(
                            "select u from UserModel u where u.userName = :username", UserModel.class)
                    .setParameter("username", userName)
                    .getSingleResult();
        } catch (NoResultException e) {
           e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserModel getUserById(Integer id) {
        try {
            return entityManager.createQuery(
                            "select u from UserModel u where u.id = :id", UserModel.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        return null;
    }
}
