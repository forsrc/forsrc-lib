package com.forsrc.core.web.user.service.impl;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.forsrc.core.web.user.dao.UserDao;
import com.forsrc.core.web.user.dao.UserPrivacyDao;
import com.forsrc.core.web.user.service.UserService;
import com.forsrc.pojo.User;
import com.forsrc.pojo.UserPrivacy;

@Transactional
@Service
public class UserServicImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserPrivacyDao userPrivacyDao;

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public void save(User user, byte[] password) {
        System.out.println(MessageFormat.format("---> has {0} users.", userDao.count()));
        userDao.save(user);
        UserPrivacy userPrivacy = new UserPrivacy();
        userPrivacy.setUserId(user.getId());
        byte[] username = user.getUsername().getBytes();
        userPrivacy.setPassword(getPassword(username, password));
        userPrivacy.setUsername(user.getUsername());
        userPrivacyDao.save(userPrivacy);
        System.out.println(MessageFormat.format("---> has {0} users.", userDao.count()));
        // throw new RuntimeException("Test Transactional");
    }

    private String getPassword(byte[] username, byte[] password) {
        byte[] split = "/".getBytes();
        byte[] pwd = new byte[username.length + split.length + password.length + 1];
        System.arraycopy(username, 0, pwd, 0, username.length);
        System.arraycopy(split, 0, pwd, username.length, split.length);
        System.arraycopy(password, 0, pwd, username.length + split.length, password.length);
        String pw = DigestUtils.md5Hex(pwd);
        return pw;
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public User get(long id) {
        return userDao.get(id);
    }

    @Override
    public List<User> get(int start, int size) {
        // return userDao.get("select '******' as password, user.id,
        // user.username, user.email from com.forsrc.pojo.User user", null,
        // start, size);
        return userDao.get(start, size);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public UserPrivacy findByUsername(String username) {
        return userPrivacyDao.findByUsername(username);
    }

    @Override
    public long count() {
        return userDao.count();
    }
}
