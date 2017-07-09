/*
 * Copyright (C) 2014
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package cn.edu.hfut.dmic.webcollector.souplang.nodes;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.hfut.dmic.webcollector.souplang.Context;
import cn.edu.hfut.dmic.webcollector.souplang.LangNode;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author
 */
public class SLAttribute extends LangNode {
	public static final Logger LOG = LoggerFactory.getLogger(SLAttribute.class);
	public String attributeName = null;
	public String regex = null;
	public Integer group = null;

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

	public void readAttribute(org.w3c.dom.Element xmlElement) {
		attributeName = xmlElement.getAttribute("attr");
		if (attributeName.isEmpty()) {
			attributeName = null;
		}
	}

	@Override
	public Object process(Object input, Context context) throws InputTypeErrorException {
		if (attributeName == null) {
			return null;
		}
		Element jsoupElement = null;
		Elements jsoupElements = null;

		if (input instanceof Element) {
			jsoupElement = (Element) input;
			if (!jsoupElement.hasAttr(attributeName)) {
				return null;
			}
			String result = jsoupElement.attr(attributeName);
			result = extractByRegex(result);
			return result;
		} else {
			jsoupElements = (Elements) input;
			ArrayList<String> result = new ArrayList<String>();
			for (Element ele : jsoupElements) {
				if (ele.hasAttr(attributeName)) {
					result.add(ele.attr(attributeName));
				}
			}
			return result;
		}

	}

	public void readRegex(org.w3c.dom.Element xmlElement) {
		regex = xmlElement.getAttribute("regex");
		if (regex.isEmpty()) {
			regex = null;
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
	public boolean validate(Object input) throws Exception {
		if (!(input instanceof Element) && !(input instanceof Elements)) {
			return false;
		}
		return true;
	}

}
