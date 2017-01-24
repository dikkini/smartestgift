package com.paymybill.controller.model;

import com.paymybill.validator.GoalDatesConstraint;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@GoalDatesConstraint(message = "{validate.goalDatesConstraint}")
public class GoalNoTargetDTO {

    @NotNull(message = "{validate.required}")
    private Date startDate;

    private Date endDate;

    @NotNull(message = "{validate.required}")
    @DecimalMin(value = "0.0", message = "{validate.minSize}")
    private BigDecimal startSum;

    @NotNull(message = "{validate.required}")
    @DecimalMin(value = "1.0", message = "{validate.minSize}")
    private BigDecimal endSum;

    @NotNull(message = "{validate.required}")
    @Size(max = 255, message = "{validate.maxSize}")
    private String name;

    @NotNull(message = "{validate.required}")
    @Size(max = 4000, message = "{validate.maxSize}")
    private String description;

    @NotNull(message = "{validate.required}")
    @DecimalMin(value = "1.0", message = "{validate.minSize}")
    private BigDecimal price;

    @NotNull(message = "{validate.required}")
    private int currencyId;

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

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }
}
