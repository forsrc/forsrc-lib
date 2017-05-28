package com.forsrc.core.base.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Cacheable(key = "baseServiceCache", keyGenerator = "keyGenerator")
@CacheConfig(cacheNames = "ehcache_1h")
@Transactional
public interface BaseService<E, PK extends Serializable> {

    @CachePut()
    // @CachePut(value = "baseDao", key = "#e.className + '.' + #e.id")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void save(E e);

    @CacheEvict()
    // @CacheEvict(value = "baseDao", key = "#e.className + '.' + #e.id")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void delete(E e);

    @Cacheable()
    // @Cacheable(value = "baseDao", key = "#e.className + '.' + #e.id")
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    E get(PK pk);

    @Cacheable()
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    List<E> get(int start, int size);

    @Cacheable()
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    <T> List<T> get(Class<T> cls, int start, int size);


    @CachePut()
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    void update(E e);

    @Cacheable()
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    long count();

    @Cacheable()
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    <T> long count(Class<T> cls);
}
