package com.forsrc.core.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseDao<E, PK extends Serializable> extends JpaRepository<E, PK> {

    int SIZE_MAX = 100;

    <S extends E> S save(S entity);

    void delete(E e);

    E get(PK pk);

    List<E> get(int start, int size);

    <T> List<T> get(Class<T> cls, int start, int size);

    List<E> get(String hql, Map<String, Object> params, int start, int size);

    void update(E e);

    <T> List<T> createNamedQuery(String namedQuery, Map<String, Object> params, int start, int size);

    <T> List<T> createNamedQuery(String namedQuery, Class<T> cls, Map<String, Object> params, int start, int size);

    int executeUpdateNamedQuery(String namedQuery, Map<String, Object> params);

    int executeUpdate(String hql, Map<String, Object> params);

    long count();

    <T> long count(Class<T> cls);

    Class<E> getEntityClass();
}
