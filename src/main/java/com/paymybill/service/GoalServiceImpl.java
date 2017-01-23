package com.paymybill.service;

import com.paymybill.controller.model.GoalNoTargetDTO;
import com.paymybill.controller.model.GoalTargetDTO;
import com.paymybill.dao.CurrencyDAO;
import com.paymybill.dao.GoalDAO;
import com.paymybill.dao.TargetDAO;
import com.paymybill.dao.model.Currency;
import com.paymybill.dao.model.Goal;
import com.paymybill.dao.model.Target;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
    public Goal registerNewGoal(GoalNoTargetDTO goalNoTargetDTO) {
        logger.trace("registerNewGoal method with arguments " + goalNoTargetDTO.toString());

        int currencyId = goalNoTargetDTO.getCurrencyId();

        //Currency goalCurrency = currencyDAO.findOne(Currency.class, currencyId);

        //Goal goal = new Goal(goalNoTargetDTO);

        //goalDAO.create(goal);

        return new Goal();
    }

    @Override
    public Goal registerNewGoal(GoalTargetDTO goalTargetDTO) {
        logger.trace("registerNewGoal method with arguments " + goalTargetDTO.toString());

        int currencyId = goalTargetDTO.getCurrencyId();


        //Target goalTarget = targetDAO.findOne(Target.class, targetUuid);
        //Currency goalCurrency = currencyDAO.findOne(Currency.class, currencyId);

        //Goal goal = new Goal(goalTargetDTO);

        //goalDAO.create(goal);

        return new Goal();
    }

    @Override
    public Collection<Currency> getAllCurrencies() {
        return currencyDAO.findAll(Currency.class);
    }
}
