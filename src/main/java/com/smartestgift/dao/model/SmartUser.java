package com.smartestgift.dao.model;

import org.hibernate.annotations.GenericGenerator;
import sun.net.www.content.image.gif;

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
    protected String username;

    @Column
    protected Date birthDate;

    @Column
    protected String firstName;

    @Column
    protected String lastName;

    @Column
    protected String middleName;

    /**
     * TRUE - man
     * FALSE - women
     */
    @Column
    protected Boolean gender;

    @Column
    protected String address;

    @Column
    protected boolean addressVisible = false;

    @Column
    protected boolean profileVisible = true;

    @Column
    protected String cellPhone;

    @Column
    protected boolean cellPhoneVisible = false;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "smartUser", cascade = CascadeType.ALL)
    protected SmartUserDetails smartUserDetails;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_gift", joinColumns = {
            @JoinColumn(name = "useruuid", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "giftuuid",
                    nullable = false, updatable = false)})
    protected Set<Gift> gifts;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="fileid")
    protected File file;

    public SmartUser() {}

    public SmartUser(Date birthDate, String username, String firstName, String lastName, String middleName) {
        this.username = username;
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
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

    public Set<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(Set<Gift> gifts) {
        this.gifts = gifts;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
