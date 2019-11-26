package com.kgc.dao;

import com.kgc.domain.Member;
import org.apache.ibatis.annotations.Select;

public interface IMemberDao {
    @Select("select * from member where id = #{id}")
    Member findById(String id);
}
