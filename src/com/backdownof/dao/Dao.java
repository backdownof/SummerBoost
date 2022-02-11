package com.backdownof.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {
    E save(E entity);

    void update(E entity);

    boolean delete(K id);

    Optional<E> findById(int K);

    List<E> findAll();
}
