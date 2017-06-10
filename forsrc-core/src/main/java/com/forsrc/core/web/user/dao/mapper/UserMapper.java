package com.forsrc.core.web.user.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.forsrc.pojo.User;

@Mapper
public interface UserMapper {

    public List<User> findByUsername(@Param("username") String username);
}
