package edu.devlopment.cms.excel.util;

import java.lang.annotation.Retention;  
import java.lang.annotation.RetentionPolicy;  
import java.lang.annotation.Target; 

@Retention(RetentionPolicy.RUNTIME)  
@Target({ java.lang.annotation.ElementType.FIELD })  
public @interface ExcelAttribute {  
  
    /** 
     * Excel中的列名 
     *  
     * @return 
     */  
    public abstract String name();  
  
    /** 
     * 列名对应的A,B,C,D...,不指定按照默认顺序排�? 
     *  
     * @return 
     */  
    public abstract String column() default "";  
  
    /** 
     * 提示信息 
     *  
     * @return 
     */  
    public abstract String prompt() default "";  
    
    /**
     * 宽度
     * @return
     */
    public int width() default 20;
    
    
    
    
    /**
     * 是否�?要合并的数据
     * @return
     */
    public abstract boolean isMer() default false ;
    
    public int merLen() default 0 ;
  
    /** 
     * 设置只能选择不能输入的列内容 
     *  
     * @return 
     */  
    public abstract String[] combo() default {};  
  
    /** 
     * 是否导出数据 
     *  
     * @return 
     */  
    public abstract boolean isExport() default true;  
  
    /** 
     * 是否为重要字段（整列标红,�?重显示） 
     *  
     * @return 
     */  
    public abstract boolean isMark() default false;  
  
    /** 
     * 是否合计当前�? 
     *  
     * @return 
     */  
    public abstract boolean isSum() default false;  
}  
