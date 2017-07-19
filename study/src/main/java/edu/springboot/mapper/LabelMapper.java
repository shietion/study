package edu.springboot.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelMapper {
    
    @Select("SELECT * from lable")
    List<Map<String, Object>> list();
}
