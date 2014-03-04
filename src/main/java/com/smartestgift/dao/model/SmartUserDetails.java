package com.smartestgift.dao.model;

import com.restfb.types.User;
import com.smartestgift.enums.AuthProviderEnum;
import com.smartestgift.enums.RolesEnum;
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
    @Column(name = "user_uuid", unique = true, nullable = false)
    protected String userUuid;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    protected SmartUser smartUser;

    @Column(name = "username")
    protected String username;

    @Column(name = "password")
    protected String password;

    @Column(name = "email")
    protected String email;

    @Column(name = "enabled")
    protected boolean enabled = true;

    @Column(name = "social_id")
    protected String socialId;

    @Column(name = "accountNonExpired")
    protected boolean accountNonExpired = true;

    @Column(name = "credentialsNonExpired")
    protected boolean credentialsNonExpired = true;

    @Column(name = "accountNonLocked")
    protected boolean accountNonLocked = true;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "auth_provider_id")
    private AuthProvider authProvider;

    @Column(name = "registration_date")
    protected Date registrationDate;

    public SmartUserDetails() {}

    /**
     * Creating SmartUser from Facebook user
     * @param user facebook user
     */
    public SmartUserDetails(User user) {
        this.smartUser = new SmartUser(user.getBirthdayAsDate(), user.getUsername(), user.getFirstName(),
                user.getLastName(), user.getMiddleName(), user.getHometownName());
        this.socialId = user.getId();
        this.registrationDate = new Date();
        this.email = user.getEmail();
        this.role = new Role(RolesEnum.USER_ROLE.getId(), RolesEnum.USER_ROLE.getRole());
        this.authProvider = new AuthProvider(AuthProviderEnum.FACEBOOK.getId(), AuthProviderEnum.FACEBOOK.getName());
    }

    public SmartUserDetails(SmartUser smartUser, String password, String email, String socialId, Date registrationDate, Role role, AuthProvider authProvider) {
        this.smartUser = smartUser;
        this.password = password;
        this.email = email;
        this.socialId = socialId;
        this.registrationDate = registrationDate;
        this.role = role;
        this.authProvider = authProvider;
        this.username = smartUser.getUsername();
    }

    public String getUserUuid() {
        return userUuid;
    }

    public SmartUser getSmartUser() {
        return smartUser;
    }

    public String getUsername() {
        return username;
    }

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

    public String getEmail() {
        return email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartUserDetails)) return false;

        SmartUserDetails that = (SmartUserDetails) o;

        if (accountNonExpired != that.accountNonExpired) return false;
        if (accountNonLocked != that.accountNonLocked) return false;
        if (credentialsNonExpired != that.credentialsNonExpired) return false;
        if (enabled != that.enabled) return false;
        if (!authProvider.equals(that.authProvider)) return false;
        if (!email.equals(that.email)) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (!registrationDate.equals(that.registrationDate)) return false;
        if (!role.equals(that.role)) return false;
        if (!smartUser.equals(that.smartUser)) return false;
        if (socialId != null ? !socialId.equals(that.socialId) : that.socialId != null) return false;
        if (!userUuid.equals(that.userUuid)) return false;
        if (!username.equals(that.username)) return false;

        return true;
    }
}
