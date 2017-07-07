package edu.development.common.base;

import java.util.List;

/**
 * BaseService接口 Created by atminer on 2017/01/07.
 */
public interface BaseService<Record> {


    int deleteByPrimaryKey(Long id);

    int insert(Record record);

    int insertSelective(Record record);

    List<Record> selectByExample(Record example);

    Record selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Record record);


    int updateByPrimaryKey(Record record);

    int deleteByPrimaryKeys(String ids);

    void initMapper();

}