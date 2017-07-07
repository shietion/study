package edu.devlopment.cms.person.service.impl;


import edu.development.common.base.BaseServiceImpl;
import edu.devlopment.cms.person.dao.mapper.PersonGangMapper;
import edu.devlopment.cms.person.model.PersonGang;
import edu.devlopment.cms.person.service.PersonGangService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* PersonGangService实现
* Created by dujianqiao on 2017/7/5.
*/
@Service
@Transactional
public class PersonGangServiceImpl extends BaseServiceImpl<PersonGangMapper, PersonGang> implements PersonGangService {

    public static Logger logger = LoggerFactory.getLogger(PersonGangServiceImpl.class);

    @Autowired
    PersonGangMapper personGangMapper;

}