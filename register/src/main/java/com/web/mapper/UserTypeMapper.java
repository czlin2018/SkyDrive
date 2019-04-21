package com.web.mapper;

import com.web.entity.UserType;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserTypeMapper extends Mapper <UserType> {
    
}