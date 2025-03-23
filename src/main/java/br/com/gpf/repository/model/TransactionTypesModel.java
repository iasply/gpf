package br.com.gpf.repository.model;

public class TransactionTypesModel {
    private Integer id;
    private String desc;
    private Integer idUser;

    public TransactionTypesModel() {
    }

    public TransactionTypesModel(Integer id, String desc, Integer idUser) {
        this.id = id;
        this.desc = desc;
        this.idUser = idUser;
    }

    public TransactionTypesModel(String desc, Integer idUser) {
        this.desc = desc;
        this.idUser = idUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}
