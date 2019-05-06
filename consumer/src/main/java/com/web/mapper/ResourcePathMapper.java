package com.web.mapper;

import com.web.entity.ResourcePath;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ResourcePathMapper extends Mapper< ResourcePath >{
    int deleteAllPath (@Param("path") String path);

    int deleteFile (@Param("path") String path);

    int deleteFileAndPath (@Param("path") String path);
}