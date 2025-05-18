package br.com.gpf.repository.dao;

import br.com.gpf.repository.DefaultRepositoryException;
import br.com.gpf.repository.model.TransactionTypesModel;
import br.com.gpf.repository.model.UserModel;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TransactionTypesDaoImpl implements TransactionTypesDao {

    private final EntityManager entityManager;

    public TransactionTypesDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }



    @Override
    public List<TransactionTypesModel> getUserTypes(Integer userId) {
        return entityManager.createQuery(
                        "select t from TransactionTypesModel t where t.user.id = :userId", TransactionTypesModel.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public void createType(TransactionTypesModel transactionTypesModel) throws DefaultRepositoryException {
        try {
            long exists = entityManager.createQuery(
                            "select count(t) from TransactionTypesModel t where t.user.id = :userId and t.desc = :desc", Long.class)
                    .setParameter("userId", transactionTypesModel.getUser().getId())
                    .setParameter("desc", transactionTypesModel.getDesc())
                    .getSingleResult();

            if (exists > 0) {
                throw new DefaultRepositoryException("JÁ EXISTE ESSA CATEGORIA PARA ESSE USUÁRIO");
            }

            entityManager.getTransaction().begin();
            entityManager.persist(transactionTypesModel);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new DefaultRepositoryException("Erro ao criar categoria: " + e.getMessage());
        }
    }

    @Override
    public boolean alterType(Integer userId, Integer typeId, String newDesc) {
        try {
            TransactionTypesModel t = entityManager.find(TransactionTypesModel.class, typeId);
            if (t != null && t.getUser().getId().equals(userId)) {
                entityManager.getTransaction().begin();
                t.setDesc(newDesc);
                entityManager.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            return false;
        }
    }

    @Override
    public boolean deleteType(Integer userId, Integer typeId) {
        try {
            TransactionTypesModel t = entityManager.find(TransactionTypesModel.class, typeId);
            if (t != null && t.getUser().getId().equals(userId)) {
                entityManager.getTransaction().begin();
                entityManager.remove(t);
                entityManager.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            return false;
        }
    }


    @Override
    public TransactionTypesModel getTypeById(Integer id) {
        List<TransactionTypesModel> result = entityManager.createQuery(
                        "select t from TransactionTypesModel t where t.id = :id", TransactionTypesModel.class)
                .setParameter("id", id)
                .getResultList();

        return result.isEmpty() ? null : result.get(0);
    }
}
