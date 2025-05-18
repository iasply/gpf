package br.com.gpf.repository.dao;

import br.com.gpf.repository.model.TransactionModel;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TransactionDaoImpl implements TransactionDao {

    private final EntityManager entityManager;

    public TransactionDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<TransactionModel> getUserTransaction(Integer userId) {
        return entityManager.createQuery(
                        "select t from TransactionModel t where t.user.id = :userId", TransactionModel.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public boolean alterTransactionType(Integer userId, Integer oldTypeId, Integer newTypeId) {
        try {
            entityManager.getTransaction().begin();

            int updated = entityManager.createQuery(
                            "update TransactionModel t set t.transactionType.id = :newTypeId " +
                                    "where t.user.id = :userId and t.transactionType.id = :oldTypeId")
                    .setParameter("newTypeId", newTypeId)
                    .setParameter("oldTypeId", oldTypeId)
                    .setParameter("userId", userId)
                    .executeUpdate();

            entityManager.getTransaction().commit();
            return updated > 0;

        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            return false;
        }
    }

    @Override
    public boolean createTransaction(TransactionModel transactionModel) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(transactionModel);
            entityManager.getTransaction().commit();
            return true;

        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            return false;
        }
    }


}
