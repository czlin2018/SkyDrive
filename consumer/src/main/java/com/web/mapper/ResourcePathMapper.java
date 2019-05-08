package com.web.mapper;

import com.web.entity.ResourcePath;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface ResourcePathMapper extends Mapper< ResourcePath >{
    int deleteAllPath (@Param("path") String path);

    int deleteFile (@Param("path") String path);

    int deleteFileAndPath (@Param("path") String path);

    int selectFirst( @Param("nodeName") String nodeName , @Param("nodePath") String nodePath );

    List <Map <String, Object>> readPathInfoFromDb( @Param("path") String path ,
                                                    @Param("userId") String userId ,
                                                    @Param("userType") String userType );

    String getResourceId (@Param("fullPath") String fullPath);
}