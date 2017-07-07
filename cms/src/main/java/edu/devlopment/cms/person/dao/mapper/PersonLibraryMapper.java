package edu.devlopment.cms.person.dao.mapper;

import edu.devlopment.cms.person.model.PersonLibrary;

public interface PersonLibraryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PersonLibrary record);

    int insertSelective(PersonLibrary record);

    PersonLibrary selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PersonLibrary record);

    int updateByPrimaryKey(PersonLibrary record);
}