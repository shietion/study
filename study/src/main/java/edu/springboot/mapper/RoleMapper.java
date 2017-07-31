package edu.springboot.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMapper {

    @Select("select ID as roleId, ROLE_NAME as roleName, RANK as rank, MEMO as description, CREATED_DATE as createdDate, DISABLE as enable from role")
    List<Map<String,Object>> select();
    
}
