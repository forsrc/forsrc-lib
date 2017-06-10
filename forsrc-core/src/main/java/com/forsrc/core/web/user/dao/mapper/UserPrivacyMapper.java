package com.forsrc.core.web.user.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.forsrc.pojo.UserPrivacy;

@Mapper
public interface UserPrivacyMapper {

    public UserPrivacy findByUsername(@Param("username") String username);
}
