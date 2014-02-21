package com.smartestgift.dao.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by dikkini on 16.02.14.
 * Email: dikkini@gmail.com
 */
@Entity
@Table (name = "auth_provider")
public class AuthProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "auth_provider_seq_gen")
    @SequenceGenerator(name = "auth_provider_seq_gen", sequenceName = "auth_provider_id_seq")
    protected Integer id;

    @Column
    protected String name;

    @OneToMany(mappedBy = "authProvider")
    private Set<SmartUserDetails> smartUsers;

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

    public Set<SmartUserDetails> getSmartUsers() {
        return smartUsers;
    }

    public void setSmartUsers(Set<SmartUserDetails> smartUsers) {
        this.smartUsers = smartUsers;
    }
}