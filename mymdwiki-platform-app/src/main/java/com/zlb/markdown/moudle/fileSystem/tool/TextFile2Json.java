package com.zlb.markdown.moudle.fileSystem.tool;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 读取文本文件获取JSON
 * @author zhulb
 *
 */
public class TextFile2Json {
	private static GetFileTextUtf8 getFileTextUtf8=new GetFileTextUtf8();
	//private static Pattern pattern=Pattern.compile("(.*)=(.*)(\r|\r\n|$)");
	private static Pattern pattern=Pattern.compile("(.*)=(.*)");
	private TextFile2Json(){};
	public static JSONObject toJSON(String path){
		JSONObject resultObject=null;
		try {
			String text=getFileTextUtf8.getText(path);
			if(text==null)return null;
			resultObject=new JSONObject();
			Matcher matcher=pattern.matcher(text);
			while(matcher.find()){
				JSONArray jArray=resultObject.getJSONArray(matcher.group(1));
				if(jArray==null){
					jArray=new JSONArray();
					resultObject.put(matcher.group(1).trim(), jArray);
				}
				jArray.add(matcher.group(2));
			}
		} catch (IOException e) {
			return null;
		}
		
		return resultObject;
	}
	
	public static void main(String args[]){
		String aString="\" abc\"=fefe]\r\nfef=afsfef";
		Pattern pattern=Pattern.compile("(.*)=(.*)(\r|\r\n|$)");
		Matcher matcher=pattern.matcher(aString);
		while(matcher.find()){
			System.out.println(matcher.group(1)+"="+matcher.group(2));
		}
		//TextFile2Json.toJSON("D:\\app\\self\\mymdwiki\\src\\com\\zlb\\markdown\\moudle\\fileSystem\\tool\\TextFile2Json_test.txt");
		TextFile2Json.toJSON("D:\\app\\self\\mymdwiki\\WebRoot\\md\\2018-01-22-学习-java-SQL.info");
		
		System.out.println("---end---");
	}
}
