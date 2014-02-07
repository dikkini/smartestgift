package com.smartestgift.dao.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
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

    @Column
    protected String name;

    @Column
    protected BigDecimal cost;

    @Column
    protected String description;

    @ManyToOne
    @JoinColumn(name="categoryid")
    protected GiftCategory category;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "gifts")
    protected Set<SmartUser> smartUsers;

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

    public GiftCategory getCategory() {
        return category;
    }

    public void setCategory(GiftCategory category) {
        this.category = category;
    }

    public Set<SmartUser> getSmartUsers() {
        return smartUsers;
    }

    public void setSmartUsers(Set<SmartUser> smartUsers) {
        this.smartUsers = smartUsers;
    }
}
