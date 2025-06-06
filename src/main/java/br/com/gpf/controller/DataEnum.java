package br.com.gpf.controller;

import br.com.gpf.model.entity.TransactionModel;
import br.com.gpf.model.entity.TransactionTypesModel;

import java.util.List;
import java.util.Objects;

public enum DataEnum {
    NULL,
    ERROR_MSG,
    FILTER_CLASSIFICATION,
    FILTER_VALUE,
    FILTER_TYPE,
    FILTER_DESCRIPTION,
    FILTER_DATE,
    USER_TYPES,
    USER_TRANSACTIONS;


    public static String decodeString(DataEnum dataEnum, Object object) {
        switch (dataEnum) {
            case NULL,
                    ERROR_MSG,
                    FILTER_CLASSIFICATION,
                    FILTER_VALUE,
                    FILTER_TYPE,
                    FILTER_DESCRIPTION,
                    FILTER_DATE -> {
                return (String) object;
            }
            default -> throw new RuntimeException("Conversão de dados inválida");
        }
    }

    public static List<TransactionTypesModel> decodeTransactionTypes(DataEnum dataEnum, Object object) {
        if (Objects.requireNonNull(dataEnum) == DataEnum.USER_TYPES) {
            return (List<TransactionTypesModel>) object;
        }
        throw new RuntimeException("Conversão de dados inválida");
    }

    public static List<TransactionModel> decodeTransactionModel(DataEnum dataEnum, Object object) {
        if (Objects.requireNonNull(dataEnum) == USER_TRANSACTIONS) {
            return (List<TransactionModel>) object;
        }
        throw new RuntimeException("Conversão de dados inválida");
    }
}
