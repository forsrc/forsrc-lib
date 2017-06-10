package com.forsrc.core.web.user.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.forsrc.pojo.UserRole;

@Mapper
public interface UserRoleMapper {

    public List<UserRole> findByUserId(@Param("userId") Long userId);

}
