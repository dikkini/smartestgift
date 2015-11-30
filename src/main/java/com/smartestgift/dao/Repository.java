package com.smartestgift.dao;

import java.util.List;

/**
 * Created by dikkini on 27.01.14.
 * Email: dikkini@gmail.com
 */
public interface Repository<T, P> {
    T findOne(P id);

    List<T> findAll();

    T create(T created);

    void delete(T deleted);

    T update(T updated);
}
