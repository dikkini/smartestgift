package com.paymybill.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, PK extends Serializable> {
    <T> T findOne(final Class<T> type, PK id);

    <T> List<T> findAll(final Class<T> type);

    <T> T create(T created);

    void delete(T deleted);

    <T> T update(T updated);
}
