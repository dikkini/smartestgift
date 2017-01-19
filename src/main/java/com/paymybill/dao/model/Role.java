package com.paymybill.dao.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "role", catalog = "paymybilldb", schema = "public")
public class Role implements Serializable {

    private static final long serialVersionUID = 2874365798237423123L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "roles_seq_gen")
    @SequenceGenerator(name = "roles_seq_gen", sequenceName = "roles_id_seq")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "roles_privilege", joinColumns = @JoinColumn(name = "roleid", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilegeid", referencedColumnName = "id"))
    private Collection<Privilege> privileges;

    public Role() {}

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

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }
}
