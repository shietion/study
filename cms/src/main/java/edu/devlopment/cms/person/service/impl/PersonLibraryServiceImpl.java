package edu.devlopment.cms.person.service.impl;


import edu.development.common.base.BaseServiceImpl;
import edu.devlopment.cms.person.dao.mapper.PersonLibraryMapper;
import edu.devlopment.cms.person.model.PersonLibrary;
import edu.devlopment.cms.person.service.PersonLibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* PersonLibraryService实现
* Created by dujianqiao on 2017/7/5.
*/
@Service
@Transactional
public class PersonLibraryServiceImpl extends BaseServiceImpl<PersonLibraryMapper, PersonLibrary> implements PersonLibraryService {

    public static Logger logger = LoggerFactory.getLogger(PersonLibraryServiceImpl.class);

    @Autowired
    PersonLibraryMapper personLibraryMapper;

}