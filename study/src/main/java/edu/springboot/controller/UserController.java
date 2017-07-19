package edu.springboot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.springboot.mapper.UserMapper;

@Controller
public class UserController {

    @Autowired
    private UserMapper userMapper;
    
    
    @RequestMapping("/user/list")
    @ResponseBody
    public Map<String, Object> index() {
	Map<String, Object> modelMap = new HashMap<String, Object>();
	modelMap.put("list",userMapper.findAll()) ;
	modelMap.put("status", 200);
	modelMap.put("message", "成功");
	return modelMap;
    }
}
