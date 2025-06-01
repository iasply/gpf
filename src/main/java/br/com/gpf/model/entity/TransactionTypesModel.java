package br.com.gpf.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "transaction_types")
public class TransactionTypesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "description", nullable = false)
    private String desc;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    public TransactionTypesModel() {
    }

    public TransactionTypesModel(String desc, UserModel user) {
        this.desc = desc;
        this.user = user;
    }
    public TransactionTypesModel(Integer id, String desc, UserModel user) {
        this.id = id;
        this.desc = desc;
        this.user = user;
    }

    @Override
    public String toString() {
        return desc;
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

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
