package com.forsrc.core.base.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forsrc.core.base.dao.BaseDao;
import com.forsrc.core.base.service.BaseService;

@Service
@Transactional
@CacheConfig(cacheNames = "ehcache_10m")
public abstract class BaseServiceImpl<E, PK extends Serializable> implements BaseService<E, PK> {

    // @Autowired
    private BaseDao<E, PK> baseDao;

    @Override
    @CachePut(key = "#root.targetClass + '/' + #e.id")
    @CacheEvict(value = { "list" }, allEntries = true)
    public void save(E e) {
        getBaseDao().save(e);
    }

    @Override
    // @CacheEvict(key = "#e.id", value = {"baseService/list", "baseService"})
    @Caching(evict = { @CacheEvict(key = "#root.targetClass + '/' + #e.id"),
            @CacheEvict(value = "list", allEntries = true) })
    public void delete(E e) {
        getBaseDao().delete(e);
    }

    @Override
    @Cacheable(key = "#root.targetClass + '/' + #pk")
    public E get(PK pk) {
        return getBaseDao().get(pk);
    }

    @Override
    @Cacheable(value = { "list" }, key = "#root.targetClass + '/' + #start + '~' + #size")
    public List<E> get(int start, int size) {
        return getBaseDao().get(start, size);
    }

    @Override
    @Cacheable(value = { "list" }, key = "#cls + '/T/' + #start + '~' + #size")
    public <T> List<T> get(Class<T> cls, int start, int size) {
        return getBaseDao().get(cls, start, size);
    }

    @Override
    @CachePut(key = "#root.targetClass + '/' + #e.id")
    @CacheEvict(value = { "list" }, allEntries = true)
    public void update(E e) {
        getBaseDao().update(e);
    }

    @Override
    @Cacheable(value = { "list" }, key = "#root.targetClass + '/' + 'count'")
    public long count() {
        return getBaseDao().count();
    }

    @Override
    @Cacheable(value = { "list" }, key = "#cls + '/T/' + 'count'")
    public <T> long count(Class<T> cls) {
        return getBaseDao().count(cls);
    }

    // public BaseDao<E, PK> getBaseDao() {
    // return baseDao;
    // }
    public abstract BaseDao<E, PK> getBaseDao();

    public void setBaseDao(BaseDao<E, PK> baseDao) {
        this.baseDao = baseDao;
    }

}
