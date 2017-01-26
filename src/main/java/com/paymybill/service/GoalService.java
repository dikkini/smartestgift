package com.paymybill.service;

import com.paymybill.controller.model.GoalNoTargetDTO;
import com.paymybill.controller.model.GoalDTO;
import com.paymybill.dao.model.Currency;
import com.paymybill.dao.model.Goal;

import java.util.Collection;

public interface GoalService {


    /**
     *
     * @param goalNoTargetDTO {@link GoalNoTargetDTO}
     * @return {@link Goal}
     */
    Goal registerNewGoal(GoalNoTargetDTO goalNoTargetDTO);

    /**
     *
     * @param goalDTO {@link GoalDTO}
     * @return {@link Goal}
     */
    Goal registerNewGoal(GoalDTO goalDTO);

    /**
     * Get all available currencies as a collection
     *
     * @return {@link Collection<Currency>}
     */
    Collection<Currency> getAllCurrencies();
}
