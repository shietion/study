package cn.edu.hfut.dmic.webcollector.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constant {

	public static final String JDBCTEMP = "template";

	public static final String CRAWLPATH = "/home/crawler/data";

	public static final String JDBC_UNAME = "root";

	public static final String JDBC_PWD = "123456";

	public static final int JDBC_INITIALSIZE = 5;

	public static final int JDBC_MAXACTIVE = 20;

	public static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/cms?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("[还款方式：]");
		String inputStr = "还款方式：按月付息，到期还本";
		Matcher matcher = pattern.matcher(inputStr);
		System.out.println(matcher.replaceAll(""));
	}
}
