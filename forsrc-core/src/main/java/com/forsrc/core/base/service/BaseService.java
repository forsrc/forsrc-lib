package com.forsrc.core.base.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface BaseService<E, PK extends Serializable> {

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void save(E e);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void delete(E e);

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    E get(PK pk);


    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    List<E> get(int start, int size);

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    <T> List<T> get(Class<T> cls, int start, int size);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void update(E e);

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    long count();

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    <T> long count(Class<T> cls);
}
