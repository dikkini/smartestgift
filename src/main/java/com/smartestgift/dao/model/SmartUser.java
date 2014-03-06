package com.smartestgift.dao.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.Set;

/**
 * Created by dikkini on 10.06.13.
 * Email: dikkini@gmail.com
 */
@Entity
@Table(name = "users")
public class SmartUser implements Serializable, Annotation {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    protected String uuid;

    @Column(name = "username")
    protected String username;

    @Column(name = "birth_date")
    protected Date birthDate;

    @Column(name = "first_name")
    protected String firstName;

    @Column(name = "last_name")
    protected String lastName;

    @Column(name = "middle_name")
    protected String middleName;

    /**
     * TRUE - man
     * FALSE - women
     */
    @Column(name = "gender")
    protected Boolean gender;

    @Column(name = "address")
    protected String address;

    @Column(name = "address_visible")
    protected boolean addressVisible = false;

    @Column(name = "profile_visible")
    protected boolean profileVisible = true;

    @Column(name = "cellPhone")
    protected String cellPhone;

    @Column(name = "cellPhone_visible")
    protected boolean cellPhoneVisible = false;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "smartUser", cascade = CascadeType.ALL)
    protected SmartUserDetails smartUserDetails;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pk.user", cascade=CascadeType.ALL)
    protected Set<SmartUserGift> smartUserGifts;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="file_id")
    protected File file;

    public SmartUser() {}

    public SmartUser(Date birthDate, String username, String firstName, String lastName, String middleName, String address) {
        this.username = username;
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.address = address;
    }

    public String getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
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

    public boolean isAddressVisible() {
        return addressVisible;
    }

    public void setAddressVisible(boolean addressVisible) {
        this.addressVisible = addressVisible;
    }

    public boolean isProfileVisible() {
        return profileVisible;
    }

    public void setProfileVisible(boolean profileVisible) {
        this.profileVisible = profileVisible;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public boolean isCellPhoneVisible() {
        return cellPhoneVisible;
    }

    public void setCellPhoneVisible(boolean cellPhoneVisible) {
        this.cellPhoneVisible = cellPhoneVisible;
    }

    public SmartUserDetails getSmartUserDetails() {
        return smartUserDetails;
    }

    public void setSmartUserDetails(SmartUserDetails authDetails) {
        this.smartUserDetails = authDetails;
    }

    public Set<SmartUserGift> getSmartUserGifts() {
        return this.smartUserGifts;
    }

    public void setSmartUserGifts(Set<SmartUserGift> smartUserGifts) {
        this.smartUserGifts = smartUserGifts;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartUser)) return false;

        SmartUser smartUser = (SmartUser) o;

        if (addressVisible != smartUser.addressVisible) return false;
        if (cellPhoneVisible != smartUser.cellPhoneVisible) return false;
        if (profileVisible != smartUser.profileVisible) return false;
        if (address != null ? !address.equals(smartUser.address) : smartUser.address != null) return false;
        if (birthDate != null ? !birthDate.equals(smartUser.birthDate) : smartUser.birthDate != null) return false;
        if (cellPhone != null ? !cellPhone.equals(smartUser.cellPhone) : smartUser.cellPhone != null) return false;
        if (!firstName.equals(smartUser.firstName)) return false;
        if (gender != null ? !gender.equals(smartUser.gender) : smartUser.gender != null) return false;
        if (lastName != null ? !lastName.equals(smartUser.lastName) : smartUser.lastName != null) return false;
        if (middleName != null ? !middleName.equals(smartUser.middleName) : smartUser.middleName != null) return false;
        if (!username.equals(smartUser.username)) return false;
        if (!uuid.equals(smartUser.uuid)) return false;

        return true;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
