package edu.springboot.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.springboot.mapper.LabelMapper;
import edu.springboot.service.LabelService;

@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelMapper mapper ;
    
    @Override
    public List<Map<String, Object>> list() {
	// TODO Auto-generated method stub
	return mapper.list() ;
    }

}
