package com.smartestgift.dao.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dikkini on 27.01.14.
 * Email: dikkini@gmail.com
 */
@Entity
@Table(name = "user_roles")
public class UserRole implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;

    @Id
    @GeneratedValue(generator = "system-uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    protected String uuid;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "role", referencedColumnName = "role")
    private Role role;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "username", referencedColumnName = "username")
    protected SmartUser smartUser;

    public UserRole() {}

    public UserRole(Role role, SmartUser smartUser) {
        this.role = role;
        this.smartUser = smartUser;
    }

    public String getUuid() {
        return uuid;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public SmartUser getSmartUser() {
        return smartUser;
    }

    public void setSmartUser(SmartUser smartUser) {
        this.smartUser = smartUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        if (role != null ? !role.equals(userRole.role) : userRole.role != null) return false;
        if (smartUser != null ? !smartUser.equals(userRole.smartUser) : userRole.smartUser != null) return false;
        if (uuid != null ? !uuid.equals(userRole.uuid) : userRole.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (smartUser != null ? smartUser.hashCode() : 0);
        return result;
    }
}
