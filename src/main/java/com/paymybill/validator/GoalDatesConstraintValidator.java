package com.paymybill.validator;


import com.paymybill.controller.model.GoalNoTargetDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GoalDatesConstraintValidator implements ConstraintValidator<GoalDatesConstraint, Object> {

    private Logger logger = Logger.getLogger(this.getClass());

    private final ApplicationContext context;

    @Autowired
    public GoalDatesConstraintValidator(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void initialize(GoalDatesConstraint arg0) {}

    @Override
    public boolean isValid(Object candidate, ConstraintValidatorContext arg1) {
        GoalNoTargetDTO goalNoTargetDTO = (GoalNoTargetDTO) candidate;

        String startDate = goalNoTargetDTO.getStartDate();
        String endDate = goalNoTargetDTO.getEndDate();

        DateFormat df = new SimpleDateFormat(context.getMessage("label.dateformat", null, LocaleContextHolder.getLocale()));
        try {

            /*
            Goal can be without end date, but it could not be without start date - it is mandatory field
             */
            if (startDate == null && endDate == null) {
                return false;
            } else if (startDate == null) {
                return false;
            }

            Calendar todayDateCal = Calendar.getInstance();

            Calendar startDateCal = Calendar.getInstance();
            startDateCal.setTime(df.parse(startDate));

            if (startDateCal.compareTo(todayDateCal) < 0) {
                logger.info("Goal Start Date in past");
                return false;
            }

            if (endDate == null) {
                return true;
            }

            Calendar endDateCal = Calendar.getInstance();
            endDateCal.setTime(df.parse(endDate));

            if (startDateCal.compareTo(endDateCal) > 0) {
                logger.error("Goal Start Date is after Goal End Date");
                return false;
            } else if (startDateCal.compareTo(endDateCal) < 0) {
                logger.info("Goal Start Date is before Goal End Date");
                return true;
            } else if (startDateCal.compareTo(endDateCal) == 0) {
                logger.error("Goal Start Date is equal to Goal End Date");
                return false;
            }
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}