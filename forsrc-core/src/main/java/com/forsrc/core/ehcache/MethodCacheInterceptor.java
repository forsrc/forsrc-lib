package com.forsrc.core.ehcache;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.InitializingBean;

import com.forsrc.core.constant.KeyConstants;
import com.forsrc.tools.SessionUtils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * The type Method cache interceptor.
 */
public class MethodCacheInterceptor implements MethodInterceptor, AfterReturningAdvice, InitializingBean {

    private static final Logger LOGGER = Logger.getLogger(MethodCacheInterceptor.class);
    private static final Set<String> removeKeySet = new HashSet<String>();
    private Cache cache;

    /**
     * Instantiates a new Method cache interceptor.
     */
    public MethodCacheInterceptor() {
        super();
    }

    /**
     * Sets cache.
     *
     * @param cache
     *            the cache
     */
    public void setCache(Cache cache) {
        this.cache = cache;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        return this.ehcache(invocation).getObjectValue();
    }

    @Override
    public void afterReturning(Object object, Method arg1, Object[] objects, Object obj) throws Throwable {

        this.removeByClassName(obj.getClass().getName());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.cache == null) {
            LOGGER.error("[Ehcache] Need a cache. Please use setCache(Cache) create it.");
        }
    }

    /**
     * [AOP] doAround()
     *
     * @param proceedingJoinPoint
     *            the proceedingJoinPoint
     * @return the cached object
     * @throws Throwable
     *             the throwable
     */
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        return this.ehcache(proceedingJoinPoint).getObjectValue();
    }

    /**
     * [AOP] doAfter()
     *
     * @param joinPoint
     *            the joinPoint
     */
    public void doAfter(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        this.removeByClassName(className);
    }

    private Element ehcache(MethodInvocation invocation) throws Throwable {
        String targetName = invocation.getThis().getClass().getName();
        String methodName = invocation.getMethod().getName();
        Object[] arguments = invocation.getArguments();
        String key = this.getCacheKey(targetName, methodName, arguments);
        Element element = null;
        synchronized (this) {
            element = this.cache.get(key);
            if (element != null) {
                LOGGER.info(String.format("[Ehcache] find cache: %s", key));
                return element;
            }
            if (null == element) {
                Object result = invocation.proceed();
                element = new Element(key, result);
                this.cache.put(element);
                String keyWithoutUsername = getCacheKeyWithoutUsername(targetName, methodName, arguments);
                removeKeySet.add(keyWithoutUsername);
            }
        }
        return element;
    }

    private Element ehcache(ProceedingJoinPoint jp) throws Throwable {
        String targetName = jp.getTarget().getClass().getName();
        String methodName = jp.getSignature().getName();
        Object[] arguments = jp.getArgs();
        String key = this.getCacheKey(targetName, methodName, arguments);
        Element element = null;
        synchronized (this) {
            element = this.cache.get(key);
            if (element != null) {
                LOGGER.info(String.format("[Ehcache] find cache: %s", key));
                return element;
            }
            if (null == element) {
                Object result = jp.proceed(arguments);
                element = new Element(key, result);
                this.cache.put(element);
                String keyWithoutUsername = getCacheKeyWithoutUsername(targetName, methodName, arguments);
                removeKeySet.add(keyWithoutUsername);
            }
        }
        return element;
    }

    private String getCacheKey(String className, String methodName, Object[] arguments) {
        String username = SessionUtils.get(KeyConstants.USERNAME.getKey());
        String key = getCacheKeyWithoutUsername(className, methodName, arguments);
        if (username == null) {
            return key;
        }
        return String.format("%s/%s", username, key);
    }

    private String getCacheKeyWithoutUsername(String className, String methodName, Object[] arguments) {

        StringBuffer sb = new StringBuffer(
                className.length() + methodName.length() + 10 + (arguments == null ? 0 : arguments.length * 7));
        sb.append(className).append('.').append(methodName);
        if (arguments != null && arguments.length > 0) {
            for (int i = 0; i < arguments.length; i++) {
                sb.append(".").append(arguments[i]);
            }
        }
        return sb.toString();
    }

    private String generateRemoveKey(final String className) {
        String key = className.substring(0, className.lastIndexOf("."));
        String username = SessionUtils.get(KeyConstants.USERNAME.getKey());
        if (username != null) {
            return String.format("%s/%s", username, key);
        }
        return className;

    }

    @Deprecated
    private void remove(final String key) {
        Iterator<String> it = this.cache.getKeys().iterator();
        while (it.hasNext()) {
            String cacheKey = it.next();
            if (cacheKey.startsWith(key)) {
                this.cache.remove(cacheKey);
                LOGGER.info(String.format("[Ehcache] remove cache: %s", cacheKey));
            }
        }
    }

    private void removeByClassName(final String key) {
        Iterator<String> it = removeKeySet.iterator();
        String username = SessionUtils.get(KeyConstants.USERNAME.getKey());
        String cacheKey = null;
        boolean delete = false;
        while (it.hasNext()) {
            cacheKey = username == null ? it.next() : String.format("%s/%s", username, it.next());
            delete = this.cache.remove(cacheKey);
            LOGGER.info(MessageFormat.format("[Ehcache] remove cache: [{0}] {1}", delete, cacheKey));
        }
    }
}
