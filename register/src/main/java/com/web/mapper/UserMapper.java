package com.web.mapper;

import com.web.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserMapper extends Mapper< User > {

    String selectPassage (@Param("name") String name);
}