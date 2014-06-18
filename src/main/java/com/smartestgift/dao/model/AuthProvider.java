package com.smartestgift.dao.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by dikkini on 16.02.14.
 * Email: dikkini@gmail.com
 */
@Entity
@Table (name = "auth_provider")
public class AuthProvider implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "auth_provider_seq_gen")
    @SequenceGenerator(name = "auth_provider_seq_gen", sequenceName = "auth_provider_id_seq")
    protected Integer id;

    @Column(name = "name")
    protected String name;

    @OneToMany(mappedBy = "authProvider")
    private Set<SmartUser> smartUsers;

    public AuthProvider() {}

    public AuthProvider(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthProvider)) return false;

        AuthProvider that = (AuthProvider) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (smartUsers != null ? !smartUsers.equals(that.smartUsers) : that.smartUsers != null) return false;

        return true;
    }
}
