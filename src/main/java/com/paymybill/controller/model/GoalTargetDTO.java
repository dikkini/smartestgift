package com.paymybill.controller.model;

import com.paymybill.validator.GoalDatesConstraint;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

@GoalDatesConstraint(message = "{validate.goalDatesConstraint}")
public class GoalTargetDTO extends GoalNoTargetDTO {

    @NotNull
    private String targetUuid;

    public String getTargetUuid() {
        return targetUuid;
    }

    public void setTargetUuid(String targetUuid) {
        this.targetUuid = targetUuid;
    }
}
