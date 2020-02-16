package com.xquant.util.mystring;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyString {
	public static void main(String[] args) {
		MyString myString=new MyString("A--${name}--B C--${age}--D");
		myString.put("name", "zhulb");
		myString.put("age", "19");
		System.out.println(myString.toString());
	}
	
	private static Pattern pattern=Pattern.compile("\\$\\{(.*?)\\}");
	
	private Map<String, Object> params;
	
	private String express;
	
	public MyString(String express){
		this.express=express;
	}
	
	public void put(String key,Object value){
		if(params==null){
			params=new HashMap<String, Object>();
		}
		params.put(key, value);
	}
	
	public String toString(){
		if(this.express==null) return null;
		Matcher matcher=pattern.matcher(express);
		String result=this.express;
		while(matcher.find()){
			result=result.replace(matcher.group(0),params.get(matcher.group(1)).toString());
		}
		return result;
	}
	
}
