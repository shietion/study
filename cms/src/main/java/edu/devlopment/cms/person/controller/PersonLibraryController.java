package edu.devlopment.cms.person.controller;

import edu.development.common.base.BaseController;
import org.springframework.web.servlet.ModelAndView;
import edu.devlopment.cms.person.model.PersonLibrary;
import edu.devlopment.cms.person.service.PersonLibraryService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * personlibrarycontroller
 * Created by dujianqiao on 2017/7/5.
 */
@Controller
@RequestMapping("/manage/personlibrary")
public class PersonLibraryController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(PersonLibraryController.class);


    @Autowired
    private PersonLibraryService service ;
    
    /**
     * 去列表 管理页面
     * 
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView index() {
      logger.info(" list index");
      ModelAndView mv = new ModelAndView("/personlibrary/index") ;
      List<PersonLibrary> list  = service.selectByExample(null) ;
      mv.addObject("list", list) ;
      return mv ;
    }
    
    /**
     * 去新增页面
     * 
     * @return
     */
    @RequestMapping("/toAdd")
    public ModelAndView toAdd() {
      logger.info(" to add ");
      ModelAndView mv = new ModelAndView("/personlibrary/toAdd");
      return mv;
    }
    
    
    
    /**
     * 保存新增
     * 
     * @param model
     * @return
     */
    @RequestMapping("/save")
    public ModelAndView save(PersonLibrary model) {
      ModelAndView mv = new ModelAndView("redirect:/index");
      service.insert(model) ;
      return mv ;
    }
    
    
    
    /**
     * 去编辑页面
     * 
     * @param id
     * @return
     */
    @RequestMapping("/toEdit")
    public ModelAndView toEdit(Long id) {
      ModelAndView mv = new ModelAndView("/personlibrary/toEdit");
      PersonLibrary model =  service.selectByPrimaryKey(id) ;
      mv.addObject("model", model) ;
      return mv ;
    }
    
    
    /**
     * 编辑信息
     * @param model
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView edit(PersonLibrary model) {
      ModelAndView mv = new ModelAndView("redirect:/index");
      service.updateByPrimaryKey(model) ;
      return mv ;
    }
    
}