package edu.devlopment.cms.excel.test;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.devlopment.cms.excel.util.ExcelUtil;

public final class Test {

    public static void main(String[] args) throws Exception {
        String pack_name = "com.isa.cms.dev" ;
        pack_name = pack_name.substring(pack_name.lastIndexOf(".")+1) ;
        System.out.println(pack_name);
    }
    
    public static void excel() throws Exception {
        ExcelUtil<AreaModel> util = new ExcelUtil<AreaModel>(AreaModel.class) ;
        InputStream input = Test.class.getResourceAsStream("throngarea.xls") ;
        List<AreaModel> areas = util.getExcelToList("人群监测配置信息", input) ;
        int len = null != areas ? areas.size() : 0 ;
        for(int i=0 ;i<len ;i++ ) {
            AreaModel area = areas.get(i) ;
            System.out.println(area.toString());
        }
        FileOutputStream fos = new FileOutputStream("d:/throngarea.xls") ;
        Map<String, Object> config = new HashMap<String, Object>() ;
        config.put("深圳市", 4) ;
        config.put("光明分区", 2) ;
        config.put("龙岗分区", 2) ;
        config.put("政府机关", 4) ;
        config.put("title", 8) ;
        boolean flag = util.writeListToExcel(areas, "人群监测配置信息", fos, config) ;
        System.out.println(flag);
    }
}
