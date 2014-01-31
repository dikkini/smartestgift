package com.smartestgift.dao.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by dikkini on 27.01.14.
 * Email: dikkini@gmail.com
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "roles_seq_gen")
    @SequenceGenerator(name = "roles_seq_gen", sequenceName = "roles_id_seq")
    private Long id;

    @Column
    private String role;

    @OneToMany(mappedBy = "role")
    private Set<SmartUserDetails> personRoles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<SmartUserDetails> getPersonRoles() {
        return personRoles;
    }

    public void setPersonRoles(Set<SmartUserDetails> personRoles) {
        this.personRoles = personRoles;
    }

}
