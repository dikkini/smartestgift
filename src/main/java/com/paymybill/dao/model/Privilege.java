package com.paymybill.dao.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "privilege", catalog = "paymybilldb", schema = "public")
public class Privilege implements Serializable {

    private static final long serialVersionUID = 237846792387498237L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    public Privilege() {
        super();
    }

    public Privilege(final String name) {
        super();
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Privilege)) return false;

        Privilege privilege = (Privilege) o;

        if (!id.equals(privilege.id)) return false;
        return name.equals(privilege.name);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Privilege{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}