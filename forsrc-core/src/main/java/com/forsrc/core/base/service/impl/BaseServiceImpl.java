package com.forsrc.core.base.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forsrc.core.base.dao.BaseDao;
import com.forsrc.core.base.service.BaseService;

@Service
@Transactional
//@Cacheable(key = "baseServiceCache", keyGenerator = "keyGenerator")
//@CacheConfig(cacheNames = "ehcache_1h")
public class BaseServiceImpl<E, PK extends Serializable> implements BaseService<E, PK> {

    // @Autowired
    private BaseDao<E, PK> baseDao;

    @Override
    //@CachePut()
    public void save(E e) {
        getBaseDao().save(e);
    }

    @Override
    //@CacheEvict()
    public void delete(E e) {
        getBaseDao().delete(e);
    }

    @Override
    //@Cacheable()
    public E get(PK pk) {
        return getBaseDao().get(pk);
    }

    @Override
    //@Cacheable()
    public List<E> get(int start, int size) {
        return getBaseDao().get(start, size);
    }

    @Override
    //@Cacheable()
    public <T> List<T> get(Class<T> cls, int start, int size) {
        return getBaseDao().get(cls, start, size);
    }

    @Override
    //@CachePut()
    public void update(E e) {
        getBaseDao().update(e);
    }

    @Override
    //@Cacheable()
    public long count() {
        return getBaseDao().count();
    }

    @Override
    //@Cacheable()
    public <T> long count(Class<T> cls) {
        return getBaseDao().count(cls);
    }

    public BaseDao<E, PK> getBaseDao() {
        return baseDao;
    }

    public void setBaseDao(BaseDao<E, PK> baseDao) {
        this.baseDao = baseDao;
    }

}
