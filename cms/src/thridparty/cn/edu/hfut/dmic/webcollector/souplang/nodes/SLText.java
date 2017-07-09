package cn.edu.hfut.dmic.webcollector.souplang.nodes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.edu.hfut.dmic.webcollector.souplang.Context;
import cn.edu.hfut.dmic.webcollector.souplang.LangNode;

/**
 * 改造类 主要对应xml 标签 <text></text> 标签定义属性 有 name 标签对应名称 ，regex 标签对应解析的正则式 ， group
 * 标签对应正则式的 过滤参数 percent 针对如果是 xxx% 格式的字符串 是否转化为百分比小数格式 replaceEmpty
 * 如果有值，将其值替换为空字符串
 * 
 * @author djq
 *
 *         2015年2月14日 下午8:01:19
 */
public class SLText extends LangNode {

	public static final Logger LOG = LoggerFactory.getLogger(SLText.class);
	public String regex = null;

	// 抽取标题 这里抽取到的标题中有一些不需要的东西，可以利用正则过滤
	// group表示正则抽取中的group(group为0对应整个匹配字符串，然后依次
	// 为各个括号中的内容，默认group="1"
	public Integer group = null;

	public String percent = null;

	public String replaceEmpty = null;

	/**
	 * 解析 group 属性值
	 * 
	 * @param xmlElement
	 */
	public void readGroup(org.w3c.dom.Element xmlElement) {
		String groupAttr = xmlElement.getAttribute("group");
		if (!groupAttr.isEmpty()) {
			group = Integer.valueOf(groupAttr);
		}
	}

	/**
	 * 解析 正则表达式属性值
	 * 
	 * @param xmlElement
	 */
	public void readRegex(org.w3c.dom.Element xmlElement) {
		regex = xmlElement.getAttribute("regex");
		if (regex.isEmpty()) {
			regex = null;
		}
	}

	/**
	 * 解析 是否需要转化百分比 如果 percent ！= null && percent == ‘true’ 将 值类型 80%转化为 0.80 类型
	 * 
	 * @param xmlElement
	 */
	public void readPercent(org.w3c.dom.Element xmlElement) {
		percent = xmlElement.getAttribute("percent");
		if (percent.isEmpty()) {
			percent = null;
		}
	}

	/**
	 * 标签中 replaceEmpty 如果有值，将其值替换为空字符串
	 * 
	 * @param xmlElement
	 */
	public void readRplaceEmpty(org.w3c.dom.Element xmlElement) {
		replaceEmpty = xmlElement.getAttribute("replaceEmpty");
		if (replaceEmpty.isEmpty()) {
			replaceEmpty = null;
		}
	}

	public String extractByRegex(String inputStr) {
		if (regex == null) {
			return inputStr;
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.find()) {
			if (group == null) {
				StringBuilder sb = new StringBuilder();
				sb.append(matcher.replaceAll("").trim());
				return sb.toString();
			} else {
				return matcher.group(group);
			}

		} else {
			return null;
		}
	}

	@Override
	public Object process(Object input, Context context) throws InputTypeErrorException {
		if (input == null) {
			return null;
		}
		String result = null;
		if (input instanceof Element) {
			Element jsoupElement = (Element) input;
			result = jsoupElement.text();
		} else if (input instanceof Elements) {
			Elements jsoupElements = (Elements) input;
			result = jsoupElements.text();
		} else if (input instanceof TextNode) {
			TextNode jsoupTextNode = (TextNode) input;
			result = jsoupTextNode.text();
		}
		if (result == null) {
			result = input.toString();
		}

		String obj = extractByRegex(result);
		if (replaceEmpty != null) { // 是否需要转化百分比
			obj = obj.replace(replaceEmpty, "");
		}

		if (percent != null && percent.equals("true")) { // 是否需要转化百分比
			obj = obj.replaceAll("%", "");
			Float f = Float.valueOf(obj);
			return f / 100;
		}

		return obj;
	}

	@Override
	public boolean validate(Object input) throws Exception {
		return true;
	}

}
