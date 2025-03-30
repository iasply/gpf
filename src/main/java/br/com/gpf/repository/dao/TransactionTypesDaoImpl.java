package br.com.gpf.repository.dao;

import br.com.gpf.repository.DefaultRepositoryException;
import br.com.gpf.repository.model.TransactionTypesModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionTypesDaoImpl implements TransactionTypesDao {
    private final List<TransactionTypesModel> list;
    private int id = 0;

    public TransactionTypesDaoImpl() {
        this.list = new ArrayList<>();
    }


    @Override
    public void createType(TransactionTypesModel transactionTypesModel) throws DefaultRepositoryException {
        list.stream()
                .filter(t -> t.getIdUser().equals(transactionTypesModel.getIdUser())
                        && t.getDesc().equals(transactionTypesModel.getDesc()))
                .findFirst()
                .ifPresent(t -> {
                    throw new DefaultRepositoryException("JÁ EXISTE ESSA CATEGORIA PARA ESSE USUÁRIO");
                });
        transactionTypesModel.setId(id);
        id++;
        list.add(transactionTypesModel);
    }

    @Override
    public List<TransactionTypesModel> getUserTypes(Integer userId) {
        return list.stream()
                .filter(t -> t.getIdUser().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public boolean alterType(Integer userId, Integer typeId, String newDesc) {
        Optional<TransactionTypesModel> transaction = list.stream()
                .filter(t -> t.getIdUser().equals(userId) && t.getId().equals(typeId))
                .findFirst();

        if (transaction.isPresent()) {
            TransactionTypesModel t = transaction.get();
            t.setDesc(newDesc);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteType(Integer userId, Integer typeId) {
        Optional<TransactionTypesModel> transactionToRemove = list.stream()
                .filter(t -> t.getIdUser().equals(userId) && t.getId().equals(typeId))
                .findFirst();

        if (transactionToRemove.isPresent()) {
            list.remove(transactionToRemove.get());
            return true;
        }

        return false;
    }


}
