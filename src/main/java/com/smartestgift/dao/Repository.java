package com.smartestgift.dao;

import java.util.List;

/**
 * Created by dikkini on 27.01.14.
 * Email: dikkini@gmail.com
 */
public interface Repository<T, P> {
    T find(P id);

    List<T> findAll();

    void store(T dmodel);

    void delete(T dmodel);
}
