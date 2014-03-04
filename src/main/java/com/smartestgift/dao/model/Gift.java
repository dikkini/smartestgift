package com.smartestgift.dao.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * Created by dikkini on 06.02.14.
 * Email: dikkini@gmail.com
 */
@Entity
@Table(name = "gift")
public class Gift implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "uuid", unique = true)
    protected String uuid;

    @Column(name = "name")
    protected String name;

    @Column
    protected BigDecimal cost;

    @Column(name = "description")
    protected String description;

    @Column(name = "add_date")
    protected Date addDate;

    @ManyToOne
    @JoinColumn(name="category_id")
    protected GiftCategory category;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pk.gift")
    protected Set<SmartUserGift> smartUserGifts;

    @ManyToMany(fetch = FetchType.EAGER,  cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "gift_file",
            joinColumns = {
                    @JoinColumn(name = "gift_uuid", nullable = false, updatable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "file_id", nullable = false, updatable = false)
            }
    )
    protected Set<File> files;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public GiftCategory getCategory() {
        return category;
    }

    public void setCategory(GiftCategory category) {
        this.category = category;
    }

    public Set<SmartUserGift> getSmartUserGifts() {
        return smartUserGifts;
    }

    public void setSmartUserGifts(Set<SmartUserGift> smartUserGifts) {
        this.smartUserGifts = smartUserGifts;
    }

    public Set<File> getFiles() {
        return files;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gift)) return false;

        Gift gift = (Gift) o;

        if (!addDate.equals(gift.addDate)) return false;
        if (!cost.equals(gift.cost)) return false;
        if (!description.equals(gift.description)) return false;
        if (!name.equals(gift.name)) return false;
        if (!uuid.equals(gift.uuid)) return false;

        return true;
    }
}
