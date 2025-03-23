package br.com.gpf.service;

import br.com.gpf.repository.model.TransactionTypesModel;

import java.util.List;
import java.util.Objects;

public enum DataEnum {
    NULL,
    ERROR_MSG,
    USER_TYPES, USER_TRANSACTIONS;



    public static String decodeString(DataEnum dataEnum, Object object){
        switch (dataEnum){
            case NULL, ERROR_MSG -> {
                return (String) object;
            }
            default -> throw new RuntimeException("invalid data parse");
        }
    }

    public static List<TransactionTypesModel> decodeTransactionTypes(DataEnum dataEnum, Object object){
        if (Objects.requireNonNull(dataEnum) == DataEnum.USER_TYPES) {
            return (List<TransactionTypesModel>) object;
        }
        throw new RuntimeException("invalid data parse");
    }
}
