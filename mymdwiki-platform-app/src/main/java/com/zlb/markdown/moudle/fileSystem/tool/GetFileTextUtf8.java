package com.zlb.markdown.moudle.fileSystem.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetFileTextUtf8 {
	public GetFileTextUtf8(){}
	/**
	 * 
	 * @param path
	 * @return
	 * @throws IOException 
	 */
	public String getText(String path) throws IOException{
		if(!new File(path).exists())return null;
		
		BufferedReader buf=null;
		StringBuilder sb=null;
		String result;
		try{
			buf=new BufferedReader(
					new InputStreamReader(
							new FileInputStream(
									new File(path)),"utf-8"));
			if(buf==null)return null;
			char[] data=new char[1024*4];
			int len=0;
			
			sb=new StringBuilder();
			while((len=buf.read(data))!=-1){
				sb.append(new String(data,0,len));
			}
			result=sb.toString();
		}catch (Exception e) {
			result=null;
		}finally{
			if(null!=buf)buf.close();
		}
		return result;
	}
	
	public static void main(String[] args) throws IOException{
		//String text=new GetFileTextUtf8().getText("C:/mymdwiki/mymdwiki/src/com/zlb/markdown/plugin/tool/utf-8File.txt");
		String text=new GetFileTextUtf8().getText("C:/mymdwiki/mymdwiki/WebRoot/md/markdown案例/Editor.md");
		System.out.println(text);
	}
}
