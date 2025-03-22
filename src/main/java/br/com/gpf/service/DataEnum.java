package br.com.gpf.service;

public enum DataEnum {
    NULL,
    ERROR_MSG;



    public static String decode(DataEnum dataEnum, Object object){
        switch (dataEnum){
            case NULL, ERROR_MSG -> {
                return (String) object;
            }
            default -> throw new RuntimeException("invalid data parse");
        }
    }

}
