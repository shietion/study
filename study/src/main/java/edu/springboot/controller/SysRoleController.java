package edu.springboot.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import edu.springboot.service.SysRoleService;

@Controller
public class SysRoleController {

    @Autowired
    private SysRoleService service;

    @RequestMapping("/role/list")
    @ResponseBody
    public Map<String, Object> index( HttpServletResponse httpResponse) {
	
	Map<String, Object> modelMap = new HashMap<String, Object>();

	modelMap.put("results", service.list());
	modelMap.put("status", 200);
	modelMap.put("message", "成功");
	return modelMap;
    }

}
