package com.web.mapper;

import com.web.entity.Resource;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface ResourceMapper extends Mapper<Resource> {

    int getSourceNum (@Param("userId") String userId);

    List <Map <String, Object>> getUploadingSource( @Param("userId") String userId );

    void updateStatus( @Param("resource") Resource resource );
}