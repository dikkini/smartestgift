package com.paymybill.dao.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "auth_provider", catalog = "paymybilldb", schema = "public")
public class AuthProvider implements Serializable {

    private static final long serialVersionUID = 2346899432323513123L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "authprovider_seq_gen")
    @SequenceGenerator(name = "authprovider_seq_gen", sequenceName = "authprovider_id_seq")
    private Long id;

    @Column(name = "name")
    private String name;

    public AuthProvider() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
