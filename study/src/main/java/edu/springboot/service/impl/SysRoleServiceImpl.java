package edu.springboot.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.springboot.mapper.RoleMapper;
import edu.springboot.service.SysRoleService;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private RoleMapper dao ;
    
    @Override
    public List<Map<String, Object>> list() {
	return dao.select() ;
    }

}
