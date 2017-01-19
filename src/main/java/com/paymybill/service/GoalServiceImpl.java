package com.paymybill.service;

import com.paymybill.dao.CurrencyDAO;
import com.paymybill.dao.GoalDAO;
import com.paymybill.dao.TargetDAO;
import com.paymybill.dao.model.Currency;
import com.paymybill.dao.model.Goal;
import com.paymybill.dao.model.Target;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Service
public class GoalServiceImpl implements GoalService {

    private Logger logger = Logger.getLogger(this.getClass());

    private GoalDAO goalDAO;
    private CurrencyDAO currencyDAO;
    private TargetDAO targetDAO;

    @Autowired
    public GoalServiceImpl(GoalDAO goalDAO, CurrencyDAO currencyDAO, TargetDAO targetDAO) {
        logger.trace("GoalServiceImpl constructor");
        this.goalDAO = goalDAO;
        this.currencyDAO = currencyDAO;
        this.targetDAO = targetDAO;
    }

    @Override
    public Goal registerNewGoal(UUID billNumber, Date endDate, BigDecimal startSum, BigDecimal endSum,
                                String name, String description, BigDecimal price, Long currencyId, UUID targetUuid) {
        Assert.notNull(billNumber, "billNumber can't be null");
        Assert.notNull(targetUuid, "targetUuid can't be null");
        Assert.notNull(currencyId, "currencyId can't be null");
        Assert.notNull(price, "price can't be null");
        Assert.notNull(startSum, "startSum can't be null");

        logger.trace("registerNewGoal method with arguments {billNumber=" + billNumber.toString() + ","
                + "endDate=" + endDate + ", "
                + "startSum=" + startSum + ", "
                + "endSum=" + endSum + ", "
                + "name=" + name + ", "
                + "description=" + description + ", "
                + "price=" + price + ", "
                + "currencyId=" + currencyId + ", "
                + "targetUuid=" + targetUuid + ", "
                + "}");

        Target goalTarget = targetDAO.findOne(Target.class, targetUuid);
        Currency goalCurrency = currencyDAO.findOne(Currency.class, currencyId);

        Goal goal = new Goal(billNumber, new Date(), endDate, startSum, endSum, name, description, price, goalCurrency,
                goalTarget);

        goalDAO.create(goal);

        return goal;
    }
}
