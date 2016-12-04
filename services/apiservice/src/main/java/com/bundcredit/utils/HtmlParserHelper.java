package com.bundcredit.utils;

import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;

public class HtmlParserHelper {

	public static NodeList getContentByTag(String content,String tagName,String attrName,String attrValue)  throws Exception {  
		Parser parser = Parser.createParser(content, "utf-8");  
		AndFilter filter = new AndFilter(new TagNameFilter(tagName), new HasAttributeFilter(attrName,attrValue));  
		NodeList nodeList = parser.extractAllNodesThatMatch(filter);  
		return nodeList;  
	}  

}
