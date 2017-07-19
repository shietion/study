package edu.springboot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

    @RequestMapping(value="/hello/simple",method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> home(@RequestBody String param) {
	System.out.println(param);
	Map<String, Object> modelMap = new HashMap<String, Object>();
	modelMap.put("status", 200);
	modelMap.put("message", "成功");

	return modelMap;
    }

}
