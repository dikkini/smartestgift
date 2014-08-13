package com.smartestgift.dao.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.restfb.types.User;
import com.smartestgift.handler.JsonUserSerializer;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by dikkini on 10.06.13.
 * Email: dikkini@gmail.com
 */
@Entity
@Table(name = "users")
@JsonSerialize(using = JsonUserSerializer.class)
public class SmartUser implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    protected String uuid;

    @Column(name = "username")
    protected String username;

    @Column(name = "password")
    protected String password;

    @Column(name = "email")
    protected String email;

    @Column(name = "enabled")
    protected boolean enabled = true;

    @Column(name = "auth_provider_id")
    private int authProvider;

    @Column(name = "registration_date")
    protected Date registrationDate;

    @Column(name = "social_id")
    protected String socialId;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "smartUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SmartUserGift> smartUserGifts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "smartUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SmartUserFriend> smartUserFriends = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "friendUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SmartUserFriend> smartUserFriendsOf = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "smartUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRole> userRoles = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "file_id")
    protected File file;

    public SmartUser() {}

    public SmartUser(String username, String password, String email, String lastName, String firstName, Date registrationDate,
                     int authProvider, boolean enabled) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.registrationDate = registrationDate;
        this.authProvider = authProvider;
        this.enabled = enabled;
    }

    public SmartUser(User facebookUser) {
        this.username = facebookUser.getUsername();
        this.firstName = facebookUser.getFirstName();
        this.lastName = facebookUser.getLastName();
        this.middleName = facebookUser.getMiddleName();
        this.address = facebookUser.getLocation().getName();
        this.birthDate = facebookUser.getBirthdayAsDate();
        this.email = facebookUser.getEmail();
        this.socialId = facebookUser.getId();
        this.registrationDate = new Date();
    }

    public String getUuid() {
        return uuid;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(int authProvider) {
        this.authProvider = authProvider;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
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

    public Set<SmartUserGift> getSmartUserGifts() {
        return smartUserGifts;
    }

    public void setSmartUserGifts(Set<SmartUserGift> smartUserGifts) {
        this.smartUserGifts = smartUserGifts;
    }

    public Set<SmartUserFriend> getSmartUserFriends() {
        return smartUserFriends;
    }

    public void setSmartUserFriends(Set<SmartUserFriend> smartUserFriends) {
        this.smartUserFriends = smartUserFriends;
    }

    public Set<SmartUserFriend> getSmartUserFriendsOf() {
        return smartUserFriendsOf;
    }

    public void setSmartUserFriendsOf(Set<SmartUserFriend> smartUserFriendsOf) {
        this.smartUserFriendsOf = smartUserFriendsOf;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Gets a builder which is used to create Person objects.
     * @param username
     * @param email
     * @param password
     * @param authProviderId
     * @return
     */
    public static Builder getBuilder(String username, String email, String password, String firstName, String lastName,
                                     Integer authProviderId, Date registrationDate) {
        return new Builder(username, email, password, firstName, lastName, authProviderId, registrationDate);
    }

    /**
     * A Builder class used to create new Person objects.
     */
    public static class Builder {
        private SmartUser built;

        // TODO билдеры для обновления информации о пользователе

        /**
         * Creates a new Builder instance for register user
         * @param username
         * @param email
         * @param password
         * @param firstName
         * @param lastName
         * @param authProviderId
         * @param registrationDate
         */
        Builder(String username, String email, String password, String firstName, String lastName, int authProviderId,
                Date registrationDate) {
            built = new SmartUser();
            built.username = username;
            built.email = email;
            built.password = password;
            built.firstName = firstName;
            built.lastName = lastName;
            built.authProvider = authProviderId;
            built.registrationDate = registrationDate;
        }

        /**
         * Builds the new Person object.
         * @return  The created Person object.
         */
        public SmartUser build() {
            return built;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmartUser smartUser = (SmartUser) o;

        if (addressVisible != smartUser.addressVisible) return false;
        if (cellPhoneVisible != smartUser.cellPhoneVisible) return false;
        if (enabled != smartUser.enabled) return false;
        if (profileVisible != smartUser.profileVisible) return false;
        if (address != null ? !address.equals(smartUser.address) : smartUser.address != null) return false;
        if (birthDate != null ? !birthDate.equals(smartUser.birthDate) : smartUser.birthDate != null) return false;
        if (cellPhone != null ? !cellPhone.equals(smartUser.cellPhone) : smartUser.cellPhone != null) return false;
        if (email != null ? !email.equals(smartUser.email) : smartUser.email != null) return false;
        if (file != null ? !file.equals(smartUser.file) : smartUser.file != null) return false;
        if (firstName != null ? !firstName.equals(smartUser.firstName) : smartUser.firstName != null) return false;
        if (gender != null ? !gender.equals(smartUser.gender) : smartUser.gender != null) return false;
        if (lastName != null ? !lastName.equals(smartUser.lastName) : smartUser.lastName != null) return false;
        if (middleName != null ? !middleName.equals(smartUser.middleName) : smartUser.middleName != null) return false;
        if (password != null ? !password.equals(smartUser.password) : smartUser.password != null) return false;
        if (registrationDate != null ? !registrationDate.equals(smartUser.registrationDate) : smartUser.registrationDate != null)
            return false;
        if (smartUserFriends != null ? !smartUserFriends.equals(smartUser.smartUserFriends) : smartUser.smartUserFriends != null)
            return false;
        if (smartUserGifts != null ? !smartUserGifts.equals(smartUser.smartUserGifts) : smartUser.smartUserGifts != null)
            return false;
        if (socialId != null ? !socialId.equals(smartUser.socialId) : smartUser.socialId != null) return false;
        if (!username.equals(smartUser.username)) return false;
        if (!uuid.equals(smartUser.uuid)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (enabled ? 1 : 0);
        result = 31 * result + authProvider;
        result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
        result = 31 * result + (socialId != null ? socialId.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (addressVisible ? 1 : 0);
        result = 31 * result + (profileVisible ? 1 : 0);
        result = 31 * result + (cellPhone != null ? cellPhone.hashCode() : 0);
        result = 31 * result + (cellPhoneVisible ? 1 : 0);
        result = 31 * result + (file != null ? file.hashCode() : 0);
        return result;
    }
}
