package com.forsrc.core.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
@Cacheable(key = "baseDaoCache", keyGenerator = "keyGenerator")
@CacheConfig(cacheNames = "ehcache_pojo")
public interface BaseDao<E, PK extends Serializable> {

    int SIZE_MAX = 100;

    @CachePut()
    // @CachePut(value = "baseDao", key = "#e.className + '.' + #e.id")
    void save(E e);

    @CacheEvict()
    // @CacheEvict(value = "baseDao", key = "#e.className + '.' + #e.id")
    void delete(E e);

    @Cacheable()
    // @Cacheable(value = "baseDao", key = "#e.className + '.' + #e.id")
    E get(PK pk);

    @Cacheable()
    List<E> get(int start, int size);

    @Cacheable()
    <T> List<T> get(Class<T> cls, int start, int size);

    // @Cacheable(key = "baseDaoCache", keyGenerator = "keyGenerator")
    @Cacheable()
    List<E> get(String hql, Map<String, Object> params, int start, int size);

    @CachePut()
    void update(E e);

    // @Cacheable()
    <T> List<T> createNamedQuery(String namedQuery, Map<String, Object> params, int start, int size);

    // @Cacheable(key = "baseDaoCache", keyGenerator = "keyGenerator")
    @Cacheable()
    <T> List<T> createNamedQuery(String namedQuery, Class<T> cls, Map<String, Object> params, int start, int size);

    int executeUpdateNamedQuery(String namedQuery, Map<String, Object> params);

    int executeUpdate(String hql, Map<String, Object> params);

    @Cacheable()
    long count();

    @Cacheable()
    <T> long count(Class<T> cls);

    Class<E> getEntityClass();
}
