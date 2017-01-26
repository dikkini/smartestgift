package com.paymybill.service;

import com.paymybill.controller.model.GoalDTO;
import com.paymybill.controller.model.GoalNoTargetDTO;
import com.paymybill.dao.CurrencyDAO;
import com.paymybill.dao.GoalDAO;
import com.paymybill.dao.TargetDAO;
import com.paymybill.dao.UserDAO;
import com.paymybill.dao.model.Currency;
import com.paymybill.dao.model.Goal;
import com.paymybill.dao.model.Target;
import com.paymybill.dao.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class GoalServiceImpl implements GoalService {

    private Logger logger = Logger.getLogger(this.getClass());

    private final GoalDAO goalDAO;
    private final CurrencyDAO currencyDAO;
    private final TargetDAO targetDAO;

    @Autowired
    public GoalServiceImpl(GoalDAO goalDAO, CurrencyDAO currencyDAO, TargetDAO targetDAO) {
        logger.trace("GoalServiceImpl constructor");
        this.goalDAO = goalDAO;
        this.currencyDAO = currencyDAO;
        this.targetDAO = targetDAO;
    }

    @Override
    public Goal registerNewGoal(GoalNoTargetDTO goalNoTargetDTO) {
        logger.trace("registerNewGoal method with arguments " + goalNoTargetDTO.toString());

        logger.debug("Get currencyId of goalNotTargetDTO and find it in database");
        Long currencyId = goalNoTargetDTO.getCurrencyId();
        Currency goalCurrency = currencyDAO.findOne(Currency.class, currencyId);
        logger.trace("Found currency: " + goalCurrency.toString());

        logger.debug("Form target based on goal fields - name and description");
        String name = goalNoTargetDTO.getName();
        String description = goalNoTargetDTO.getDescription();
        Target goalTarget = new Target(name, description);
        logger.debug("Formed goal target: " + goalTarget.toString());
        goalTarget = targetDAO.create(goalTarget);
        logger.trace("Created goal target: " + goalTarget.toString());

        Goal goal = registerNewGoal(goalNoTargetDTO, goalTarget, goalCurrency);

        return goal;
    }

    @Override
    public Goal registerNewGoal(GoalDTO goalDTO) {
        logger.trace("registerNewGoal method with arguments " + goalDTO.toString());

        logger.debug("Get currencyId of goalNotTargetDTO and find it in database");
        Long currencyId = goalDTO.getCurrencyId();
        Currency goalCurrency = currencyDAO.findOne(Currency.class, currencyId);
        logger.trace("Found currency: " + goalCurrency.toString());

        logger.debug("Get targetUuid of goalDTO and find it in database");
        String targetUuid = goalDTO.getTargetUuid();
        Target goalTarget = targetDAO.findOne(Target.class, UUID.fromString(targetUuid));
        logger.trace("Found goal target: " + goalTarget.toString());

        Goal goal = registerNewGoal(goalDTO, goalTarget, goalCurrency);

        return goal;
    }

    @Override
    public Collection<Currency> getAllCurrencies() {
        return currencyDAO.findAll(Currency.class);
    }

    private Goal registerNewGoal(GoalNoTargetDTO goalDTO, Target goalTarget, Currency goalCurrency) {
        logger.debug("Form new Goal entity");
        Goal goal = new Goal(goalDTO, goalTarget, goalCurrency);
        goal.setBillNumber(generateBillNumber());
        goal.setPrivate(false);
        logger.debug("Goal: " + goal.toString());
        goalDAO.create(goal);
        logger.trace("New goal UUID: " + goal.getUuid().toString());

        return goal;
    }

    private UUID generateBillNumber() {
        return UUID.randomUUID();
    }
}
