package edu.devlopment.cms.person.dao.mapper;

import edu.devlopment.cms.person.model.PersonGang;

public interface PersonGangMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PersonGang record);

    int insertSelective(PersonGang record);

    PersonGang selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PersonGang record);

    int updateByPrimaryKey(PersonGang record);
}