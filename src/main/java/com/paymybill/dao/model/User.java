package com.paymybill.dao.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "user", catalog = "paymybilldb", schema = "public")
public class User implements Serializable {

    private static final long serialVersionUID = 1325687088267757690L;

    @Id
    @GeneratedValue(generator = "system-uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    private UUID uuid;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "registrationDate")
    private Date registrationDate;

    @Column(name = "birthDate")
    private Date birthDate;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "middleName")
    private String middleName;

    /**
     * TRUE - man
     * FALSE - women
     */
    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "address")
    private String address;

    @Column(name = "addressVisible")
    private Boolean addressVisible = false;

    @Column(name = "profileVisible")
    private Boolean profileVisible = true;

    @Column(name = "cellPhone")
    private String cellPhone;

    @Column(name = "cellPhoneVisible")
    private Boolean cellPhoneVisible = false;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "accountNonExpired")
    private Boolean accountNonExpired;

    @Column(name = "accountNonLocked")
    private Boolean accountNonLocked;

    @Column(name = "credentialsNonExpired")
    private Boolean credentialsNonExpired;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="roleid")
    private Role role;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "photoId")
    private File photo;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
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

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getAddressVisible() {
        return addressVisible;
    }

    public void setAddressVisible(Boolean addressVisible) {
        this.addressVisible = addressVisible;
    }

    public Boolean getProfileVisible() {
        return profileVisible;
    }

    public void setProfileVisible(Boolean profileVisible) {
        this.profileVisible = profileVisible;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public Boolean getCellPhoneVisible() {
        return cellPhoneVisible;
    }

    public void setCellPhoneVisible(Boolean cellPhoneVisible) {
        this.cellPhoneVisible = cellPhoneVisible;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }
}
