package com.forsrc.core.web.user.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.forsrc.pojo.Role;

@Mapper
public interface RoleMapper {

    public List<Role> findByUserId(@Param("userId") Long userId);
}
