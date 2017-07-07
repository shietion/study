package edu.development.util;

import static edu.development.util.StringUtil.lineToHump;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.velocity.VelocityContext;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * 代码生成类 Created by atminer on 2017/1/10.
 */
public class GeneratorUtil {

    // generatorConfig模板路径
    private static String generatorConfig_vm = "/template/generatorConfig.vm";
    // Service模板路径
    private static String service_vm = "/template/Service.vm";

    // ServiceImpl模板路径
    private static String serviceImpl_vm = "/template/ServiceImpl.vm";

    /**
     * 控制层 模板
     */
    private static String controller_vm = "/template/Controller.vm";

    /**
     * JSP 首页 模板
     */
    private static String index_vm = "/template/index.vm";

    /**
     * 
     * @param jdbc_driver
     * @param jdbc_url
     * @param jdbc_username
     * @param jdbc_password
     * @param targetProject
     *            生成目标项目
     * @param database
     * @param table_prefix
     * @param package_name
     *            包名
     * @param last_insert_id_tables
     * @throws Exception
     */
    public static void generator(String jdbc_driver, String jdbc_url, String jdbc_username, String jdbc_password, String targetProject, String database, String table_prefix,
	    String package_name, Map<String, String> last_insert_id_tables) throws Exception {

	generatorConfig_vm = GeneratorUtil.class.getResource(generatorConfig_vm).getPath().replaceFirst("/", "");
	service_vm = GeneratorUtil.class.getResource(service_vm).getPath().replaceFirst("/", "");
	serviceImpl_vm = GeneratorUtil.class.getResource(serviceImpl_vm).getPath().replaceFirst("/", "");
	index_vm = GeneratorUtil.class.getResource(index_vm).getPath().replaceFirst("/", "");

	String basePath = GeneratorUtil.class.getResource("/").getPath().replace("/target/classes/", "").replace(targetProject, "").replaceFirst("/", "");
	String generatorConfig_xml = GeneratorUtil.class.getResource("/").getPath().replace("/target/classes/", "") + "/src/main/resources/generatorConfig.xml";
	targetProject = basePath + targetProject;
	String sql = "SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '" + database + "' AND table_name LIKE '" + table_prefix + "_%';";

	System.out.println("========== 开始生成generatorConfig.xml文件 ==========");
	List<Map<String, Object>> tables = new ArrayList<>();
	try {
	    VelocityContext context = new VelocityContext();
	    Map<String, Object> table;

	    // 查询定制前缀项目的所有表
	    JdbcUtil jdbcUtil = new JdbcUtil(jdbc_driver, jdbc_url, jdbc_username, AESUtil.AESDecode(jdbc_password));
	    List<Map<String, Object>> result = jdbcUtil.selectByParams(sql, null);
	    for (Map<String, Object> map : result) {
		System.out.println(map.get("TABLE_NAME"));
		table = new HashMap<>();
		table.put("table_name", map.get("TABLE_NAME"));
		table.put("model_name", lineToHump(ObjectUtils.toString(map.get("TABLE_NAME"))));
		String colSql = "select COLUMN_NAME from  information_schema.COLUMNS where UPPER(TABLE_NAME) = '" + map.get("TABLE_NAME").toString().toUpperCase()
			+ "'  AND  TABLE_SCHEMA = 'isa'";
		List<Map<String, Object>> colMaps = jdbcUtil.selectByParams(colSql, null);
		List<String> cols = new ArrayList<String>();
		for (Map<String, Object> col : colMaps) {
		    cols.add(col.get("COLUMN_NAME").toString());
		}

		table.put("table_columns", cols);
		tables.add(table);
	    }
	    jdbcUtil.release();

	    String targetProject_sqlMap = targetProject;
	    context.put("tables", tables);
	    context.put("generator_javaModelGenerator_targetPackage", package_name + ".model");
	    context.put("generator_sqlMapGenerator_targetPackage", package_name + ".dao.mapper");
	    context.put("generator_javaClientGenerator_targetPackage", package_name + ".dao.mapper");
	    context.put("targetProject", targetProject);
	    context.put("targetProject_sqlMap", targetProject_sqlMap);
	    context.put("generator_jdbc_password", AESUtil.AESDecode(jdbc_password));
	    context.put("last_insert_id_tables", last_insert_id_tables);
	    VelocityUtil.generate(generatorConfig_vm, generatorConfig_xml, context);
	    // 删除旧代码
	    deleteDir(new File(targetProject + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/model"));
	    deleteDir(new File(targetProject + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/dao/mapper"));
	    deleteDir(new File(targetProject_sqlMap + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/dao/mapper"));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	System.out.println("========== 结束生成generatorConfig.xml文件 ==========");

	System.out.println("========== 开始运行MybatisGenerator ==========");
	List<String> warnings = new ArrayList<>();
	File configFile = new File(generatorConfig_xml);
	ConfigurationParser cp = new ConfigurationParser(warnings);
	Configuration config = cp.parseConfiguration(configFile);
	DefaultShellCallback callback = new DefaultShellCallback(true);
	MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
	myBatisGenerator.generate(null);
	for (String warning : warnings) {
	    System.out.println(warning);
	}
	System.out.println("========== 结束运行MybatisGenerator ==========");

	System.out.println("========== 开始生成Service ==========");
	String ctime = new SimpleDateFormat("yyyy/M/d").format(new Date());
	String servicePath = targetProject + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/service";
	String serviceImplPath = targetProject + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/service/impl";
	File dirSpath = new File(servicePath);
	if (!dirSpath.exists()) {
	    dirSpath.mkdir();
	}
	File dirSip = new File(serviceImplPath);
	if (!dirSip.exists()) {
	    dirSip.mkdir();
	}
	for (int i = 0; i < tables.size(); i++) {
	    String model = lineToHump(ObjectUtils.toString(tables.get(i).get("table_name")));
	    String service = servicePath + "/" + model + "Service.java";

	    String serviceImpl = serviceImplPath + "/" + model + "ServiceImpl.java";
	    // 生成service
	    File serviceFile = new File(service);
	    if (!serviceFile.exists()) {
		VelocityContext context = new VelocityContext();
		context.put("package_name", package_name);
		context.put("model", model);
		context.put("ctime", ctime);
		VelocityUtil.generate(service_vm, service, context);
		System.out.println(service);
	    }

	    // 生成serviceImpl
	    File serviceImplFile = new File(serviceImpl);
	    if (!serviceImplFile.exists()) {
		VelocityContext context = new VelocityContext();
		context.put("package_name", package_name);
		context.put("model", model);
		context.put("mapper", StringUtil.toLowerCaseFirstOne(model));
		context.put("ctime", ctime);
		VelocityUtil.generate(serviceImpl_vm, serviceImpl, context);
		System.out.println(serviceImpl);
	    }
	}
	System.out.println("========== 结束生成Service ==========");

	System.out.println("========== 开始生成Controller ==========");

	String controllerPath = targetProject + "/src/main/java/" + package_name.replaceAll("\\.", "/") + "/controller";
	File dirCotrl = new File(controllerPath);
	if (!dirCotrl.exists()) {
	    dirCotrl.mkdir();
	}
	for (int i = 0; i < tables.size(); i++) {
	    String model = lineToHump(ObjectUtils.toString(tables.get(i).get("table_name")));
	    String controller = controllerPath + "/" + model + "Controller.java";
	    // 生成service
	    File serviceFile = new File(controller);
	    if (!serviceFile.exists()) {
		VelocityContext context = new VelocityContext();
		context.put("package_name", package_name);
		context.put("modelname", model.toLowerCase());
		context.put("model", model);
		context.put("ctime", ctime);
		VelocityUtil.generate(controller_vm, controller, context);
		System.out.println(controller);
	    }

	}

	System.out.println("========== 结束生成Controller ==========");

	System.out.println("========== 开始生成index.jsp ==========");

	String jspModel =  package_name.substring(package_name.lastIndexOf(".")+1) ;
	String indexPath = targetProject + "/src/main/webapp/WEB-INF/jsp/"+jspModel+"/";
	File ipDir = new File(indexPath);
	if (!ipDir.exists()) {
	    ipDir.mkdir();
	}
	for (int i = 0; i < tables.size(); i++) {
	    String model = lineToHump(ObjectUtils.toString(tables.get(i).get("table_name")));
	    String jspPath = indexPath + "/" + model.toLowerCase() + "/" ;
	    File dipDir = new File(jspPath);
	    if (!dipDir.exists()) {
		dipDir.mkdir();
	    }
		
	    String indexjsp = jspPath + "index.jsp";
	    // 生成service
	    File indexjspFile = new File(indexjsp);
	    if (!indexjspFile.exists()) {
		VelocityContext context = new VelocityContext();
		context.put("package_name", package_name);
		context.put("modelname", model.toLowerCase());
		context.put("model", model);
		context.put("ctime", ctime);
		VelocityUtil.generate(index_vm, indexjsp, context);
		System.out.println(indexjsp);
	    }

	}

	System.out.println("========== 结束生成index.jsp ==========");

    }

    // 递归删除非空文件夹
    public static void deleteDir(File dir) {
	if (dir.isDirectory()) {
	    File[] files = dir.listFiles();
	    for (int i = 0; i < files.length; i++) {
		deleteDir(files[i]);
	    }
	}
	dir.delete();
    }

}
