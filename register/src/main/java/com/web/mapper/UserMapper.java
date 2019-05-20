package com.web.mapper;

import com.web.dto.UserDto;
import com.web.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserMapper extends Mapper< User > {

    String selectPassage (@Param("name") String name);

    List <UserDto> getList( @Param("name") String name );

    Integer deleteByIds( @Param("id") List <String> id );

    UserDto joinIn( @Param("user") User user );

    int updateSize( @Param("user") User user );

    double sizeHadUsred( @Param("userId") String userId );
}