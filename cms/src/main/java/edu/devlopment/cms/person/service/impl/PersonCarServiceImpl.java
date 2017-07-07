package edu.devlopment.cms.person.service.impl;


import edu.development.common.base.BaseServiceImpl;
import edu.devlopment.cms.person.dao.mapper.PersonCarMapper;
import edu.devlopment.cms.person.model.PersonCar;
import edu.devlopment.cms.person.service.PersonCarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* PersonCarService实现
* Created by dujianqiao on 2017/7/5.
*/
@Service
@Transactional
public class PersonCarServiceImpl extends BaseServiceImpl<PersonCarMapper, PersonCar> implements PersonCarService {

    public static Logger logger = LoggerFactory.getLogger(PersonCarServiceImpl.class);

    @Autowired
    PersonCarMapper personCarMapper;

}