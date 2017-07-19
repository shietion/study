package edu.springboot.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import edu.springboot.service.LabelService;

@Controller
public class LabelController {

    @Autowired
    private LabelService service ;
    
    @RequestMapping("/label/list")
    @ResponseBody
    public Map<String, Object> index() {
	Map<String, Object> modelMap = new HashMap<String, Object>();
	modelMap.put("list",service.list()) ;
	modelMap.put("status", 200);
	modelMap.put("message", "成功");
	return modelMap;
    }
}
