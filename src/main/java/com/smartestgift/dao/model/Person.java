package com.smartestgift.dao.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by dikkini on 10.06.13.
 * Email: dikkini@gmail.com
 */
@Entity
@Table(name = "person")
public class Person implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    protected UUID uuid;

    @Column
    protected Date birthDate;

    @Column
    protected String firstName;

    @Column
    protected String lastName;

    @Column
    protected String middleName;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "person", cascade = CascadeType.ALL)
    protected PersonAuthDetails authDetails;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public PersonAuthDetails getAuthDetails() { return authDetails; }

    public void setAuthDetails(PersonAuthDetails authDetails) { this.authDetails = authDetails; }
}
