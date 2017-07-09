package cn.edu.hfut.dmic.webcollector.crawler;

import java.io.InputStream;

import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.souplang.SoupLang;
import cn.edu.hfut.dmic.webcollector.util.Constant;
import cn.edu.hfut.dmic.webcollector.util.JDBCHelper;
import cn.edu.hfut.dmic.webcollector.util.RegexRule;

/**
 * 通用 抓标 爬虫器
 * 
 * @author djq
 *
 *         2015年6月4日 上午11:40:28
 */
public class CommonLoanCrawler extends DeepCrawler {

	public SoupLang soupLang ;
	RegexRule regexRule = new RegexRule();// 正则表达式类

	/**
	 * 爬虫抓标通用 配置文件方法 解析配置文件 获取相应的SQL 处理文件
	 * 
	 * @param pagesize
	 * @param formatSeed
	 * @param ruleSeed
	 * @param resource
	 */
	public CommonLoanCrawler(int pagesize, String formatSeed, String ruleSeed, String resource) {
		for (int i = 1; i <= pagesize; i++) {
			addSeed(String.format(formatSeed, i));
		}
		if(null != ruleSeed && !"".equals(ruleSeed)) {
			regexRule.addRule(ruleSeed);
		}
		
		try {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
			soupLang = new SoupLang(is);
			JDBCHelper.createMariaDBTemplate(Constant.JDBCTEMP);
		} catch (Exception e) {
			System.out.println("message====" + e.getMessage());
			System.out.println("异常-----------------------------------");
		}
	}

	@Override
	public Links visitAndGetNextLinks(Page page) {
		soupLang.extract(page.getDoc());
		Links nextLinks = new Links();
		nextLinks.addAllFromDocument(page.getDoc(), regexRule);
		return nextLinks;
	}

}
