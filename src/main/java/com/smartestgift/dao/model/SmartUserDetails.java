package com.smartestgift.dao.model;

import org.hibernate.annotations.GenericGenerator;

import org.hibernate.annotations.Parameter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dikkini on 1/29/14.
 * Email: dikkini@gmail.com
 */
@Entity
@Table(name = "user_details")
public class SmartUserDetails implements Serializable, UserDetails {
    @GenericGenerator(name = "generator", strategy = "foreign",
            parameters = @Parameter(name = "property", value = "smartUser"))
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "useruuid", unique = true, nullable = false)
    protected String userUuid;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    protected SmartUser smartUser;

    @Column
    protected String username;

    @Column
    protected String password;

    @Column
    protected boolean enabled = true;

    @Column
    protected boolean accountNonExpired = true;

    @Column
    protected boolean credentialsNonExpired = true;

    @Column
    protected boolean accountNonLocked = true;

    @ManyToOne
    @JoinColumn(name="roleId")
    private Role role;

    public String getUserUuid() {
        return userUuid;
    }

    @Column
    protected Date registrationDate;

    public SmartUser getSmartUser() {
        return smartUser;
    }

    public String getUsername() { return username; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO сделать много ролей
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(getRole().getRole()));
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public Role getRole() {
        return role;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public void setSmartUser(SmartUser smartUser) {
        this.smartUser = smartUser;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
}
