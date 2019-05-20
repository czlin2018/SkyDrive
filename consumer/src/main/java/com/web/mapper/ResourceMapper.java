package com.web.mapper;

import com.web.entity.Event;
import com.web.entity.Resource;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface ResourceMapper extends Mapper<Resource> {

    int getSourceNum (@Param("userId") String userId);

    List <Map <String, Object>> getUploadingSource( @Param("userId") String userId );

    void updateStatus( @Param("resource") Resource resource );

    double sizeHadUsred( @Param("userId") String userId );

    double size( @Param("userId") String userId );

    List <Event> getEvent( @Param("userId") String userId , @Param("userType") String userType );

    int updateEvent( @Param("id") String id , @Param("status") String status );

    int delEvent( @Param("id") String id );

    double sizeSetUsred( );

    double sizeHadUsredForAdmin( );

}