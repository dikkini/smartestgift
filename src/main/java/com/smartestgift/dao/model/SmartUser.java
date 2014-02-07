package com.smartestgift.dao.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by dikkini on 10.06.13.
 * Email: dikkini@gmail.com
 */
@Entity
@Table(name = "users")
public class SmartUser implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    protected String uuid;

    @Column
    protected Date birthDate;

    @Column
    protected String firstName;

    @Column
    protected String lastName;

    @Column
    protected String middleName;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "smartUser", cascade = CascadeType.ALL)
    protected SmartUserDetails smartUserDetails;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_gift", catalog = "smartestgiftdb", joinColumns = {
            @JoinColumn(name = "useruuid", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "giftuuid",
                    nullable = false, updatable = false) })
    protected Set<Gift> gifts;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
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

    public SmartUserDetails getSmartUserDetails() { return smartUserDetails; }

    public void setSmartUserDetails(SmartUserDetails authDetails) { this.smartUserDetails = authDetails; }

    public Set<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(Set<Gift> gifts) {
        this.gifts = gifts;
    }
}
