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
    protected String socialId;

    @Column
    protected boolean accountNonExpired = true;

    @Column
    protected boolean credentialsNonExpired = true;

    @Column
    protected boolean accountNonLocked = true;

    @ManyToOne
    @JoinColumn(name="roleId")
    private Role role;

    @ManyToOne
    @JoinColumn(name="authProviderId")
    private AuthProvider authProvider;

    @Column
    protected Date registrationDate;

    public SmartUserDetails() {}

    public SmartUserDetails(SmartUser smartUser, String password, Date registrationDate,  Role role, AuthProvider authProvider) {
        this.authProvider = authProvider;
        this.username = smartUser.getUsername();
        this.password = password;
        this.smartUser = smartUser;
        this.role = role;
        this.registrationDate = registrationDate;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public SmartUser getSmartUser() {
        return smartUser;
    }

    public String getUsername() { return username; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO сделать много ролей (?)
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

    public String getSocialId() {
        return socialId;
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

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }
}
