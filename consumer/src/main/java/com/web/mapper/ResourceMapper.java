package com.web.mapper;

import com.web.entity.Resource;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface ResourceMapper extends Mapper<Resource> {

    int getSourceNum (@Param("userId") String userId);
}