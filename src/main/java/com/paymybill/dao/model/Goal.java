package com.paymybill.dao.model;

import com.paymybill.controller.model.GoalNoTargetDTO;
import com.paymybill.controller.model.GoalTargetDTO;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "goal", catalog = "paymybilldb", schema = "public")
public class Goal implements Serializable {

    private static final long serialVersionUID = 1224486710361253530L;

    @Id
    @GeneratedValue(generator = "system-uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    private UUID uuid;

    @Column(name = "billnumber", unique = true)
    private UUID billNumber;

    @Column(name = "startdate")
    private Date startDate;

    @Column(name = "enddate")
    private Date endDate;

    @Column(name = "startsum")
    private BigDecimal startSum;

    @Column(name = "endsum")
    private BigDecimal endSum;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currencyid")
    private Currency currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "targetuuid")
    private Target target;

    @Column(name = "isPrivate")
    private Boolean isPrivate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "goal_file", joinColumns = @JoinColumn(name = "goaluuid", referencedColumnName = "uuid"),
            inverseJoinColumns = @JoinColumn(name = "fileid", referencedColumnName = "id"))
    private Collection<File> fileCollection;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="goalrefurluuid")
    private GoalRefURL goalRefURL;

    @OneToMany(mappedBy="goal")
    private Collection<GoalRefPersonalURL> goalRefPersonalURLCollection;

    public Goal() {}

    public Goal(GoalNoTargetDTO goal) {
        this.name = goal.getName();
        this.description = goal.getDescription();
    }

    public Goal(GoalTargetDTO goalTargetDTO) {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(UUID billNumber) {
        this.billNumber = billNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getStartSum() {
        return startSum;
    }

    public void setStartSum(BigDecimal startSum) {
        this.startSum = startSum;
    }

    public BigDecimal getEndSum() {
        return endSum;
    }

    public void setEndSum(BigDecimal endSum) {
        this.endSum = endSum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Collection<File> getFileCollection() {
        return fileCollection;
    }

    public void setFileCollection(Collection<File> fileCollection) {
        this.fileCollection = fileCollection;
    }

    public GoalRefURL getGoalRefURL() {
        return goalRefURL;
    }

    public void setGoalRefURL(GoalRefURL goalRefURL) {
        this.goalRefURL = goalRefURL;
    }

    public Collection<GoalRefPersonalURL> getGoalRefPersonalURLCollection() {
        return goalRefPersonalURLCollection;
    }

    public void setGoalRefPersonalURLCollection(Collection<GoalRefPersonalURL> goalRefPersonalURLCollection) {
        this.goalRefPersonalURLCollection = goalRefPersonalURLCollection;
    }
}
