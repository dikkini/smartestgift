package com.smartestgift.dao.model;

import org.hibernate.annotations.GenericGenerator;

import org.hibernate.annotations.Parameter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by dikkini on 1/29/14.
 * Email: dikkini@gmail.com
 */
@Entity
@Table(name = "personAuthDetails")
public class PersonAuthDetails implements Serializable {
    @GenericGenerator(name = "generator", strategy = "foreign",
            parameters = @Parameter(name = "property", value = "person"))
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "personuuid", unique = true, nullable = false)
    protected String personUuid;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    protected Person person;

    @Column
    protected String login;

    @Column
    protected String passwordMd5;

    @Column
    protected boolean enabled;

    @Column
    protected boolean accountNonExpired;

    @Column
    protected boolean credentialsNonExpired;

    @Column
    protected boolean accountNonLocked;;

    @ManyToOne
    @JoinColumn(name="roleId")
    private Role role;

    public String getPersonUuid() {
        return personUuid;
    }

    public void setPersonUuid(String personUuid) {
        this.personUuid = personUuid;
    }

    @Column
    protected Date registrationDate;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordMd5() {
        return passwordMd5;
    }

    public void setPasswordMd5(String passwordMd5) {
        this.passwordMd5 = passwordMd5;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
}
