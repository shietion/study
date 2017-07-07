package edu.devlopment.cms.generator.tool;

import java.util.HashMap;
import java.util.Map;

import edu.development.util.GeneratorUtil;
import edu.development.util.PropertiesFileUtil;

/**
 * 代码生成�? Created by atminer on 2017/1/10.
 */
public class Generator {

    // 根据命名规范，只修改此常量值即可
    private static String TARGET_PROJECT = "cms";
    private static String DATABASE = "isa";
    private static String TABLE_PREFIX = "person_";
    private static String PACKAGE_NAME = "edu.devlopment.cms.person";
    private static String JDBC_DRIVER = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.driver");
    private static String JDBC_URL = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.url");
    private static String JDBC_USERNAME = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.username");
    private static String JDBC_PASSWORD = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.password");
    // �?要insert后返回主键的表配置，key:表名,value:主键
    private static Map<String, String> LAST_INSERT_ID_TABLES = new HashMap<String, String>();
    static {

    }

    /**
     * 自动代码生成
     * 
     * @param args
     */
    public static void main(String[] args) throws Exception {
	GeneratorUtil.generator(JDBC_DRIVER, JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD, TARGET_PROJECT, DATABASE, TABLE_PREFIX, PACKAGE_NAME, LAST_INSERT_ID_TABLES);
    }

}
