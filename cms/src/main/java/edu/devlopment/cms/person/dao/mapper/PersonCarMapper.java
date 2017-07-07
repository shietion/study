package edu.devlopment.cms.person.dao.mapper;

import edu.devlopment.cms.person.model.PersonCar;

public interface PersonCarMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PersonCar record);

    int insertSelective(PersonCar record);

    PersonCar selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PersonCar record);

    int updateByPrimaryKey(PersonCar record);
}