package com.smartestgift.dao.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dikkini on 27.01.14.
 * Email: dikkini@gmail.com
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "roles_seq_gen")
    @SequenceGenerator(name = "roles_seq_gen", sequenceName = "roles_id_seq")
    private Integer id;

    @Column(name = "role")
    private String role;

    public Role() {}

    public Role(Integer id, String role) {
        this.id = id;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;

        Role role1 = (Role) o;

        if (!id.equals(role1.id)) return false;
        if (!role.equals(role1.role)) return false;

        return true;
    }
}
