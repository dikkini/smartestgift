package com.paymybill.service;

import com.paymybill.dao.model.Currency;
import com.paymybill.dao.model.Goal;
import com.paymybill.dao.model.Target;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public interface GoalService {


    /**
     * Register new goal require existed Target object, so it needs targetUuid.
     *
     * @param billNumber {@link Goal#billNumber}
     * @param endDate {@link Goal#endDate}
     * @param startSum {@link Goal#startSum}
     * @param endSum {@link Goal#endSum}
     * @param name {@link Goal#name}
     * @param description {@link Goal#description}
     * @param price {@link Goal#price}
     * @param currencyId {@link Currency#id}
     * @param targetUuid {@link Target#uuid}
     * @return {@link Goal}
     */
    Goal registerNewGoal(UUID billNumber, Date endDate, BigDecimal startSum, BigDecimal endSum,
                         String name, String description, BigDecimal price, Long currencyId, UUID targetUuid);
}
