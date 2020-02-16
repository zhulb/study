package com.xquant.util.gbk2utf8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import com.xquant.util.fileiterator.LimitedFileIterator;

/**
 * 测
 * @author zhulb
 *
 */
public class GBK2Utf8 {

	public static void main(String[] args) throws IOException{
		String wrapline=System.getProperty("os.name").toLowerCase().toString().contains("windows")?"\r\n":"\n";
		System.out.println(System.getProperty("os.name").toLowerCase().toString().contains("windows"));
		changeJava("");
	}
	
	public static void changeJava(String dir) throws IOException {
		LimitedFileIterator limitedFileIterator=new LimitedFileIterator();
		limitedFileIterator.add(dir);
		while(limitedFileIterator.hasNext()){
			String filepath=limitedFileIterator.next();
			File cur=new File(filepath);
			if(cur.isDirectory())continue;
			if(cur.getName().endsWith(".java")){
				change(cur.getAbsolutePath());
			}
		}
	}

	public static void change(String filePath) throws IOException{
		String filePathIn=filePath+"_in";
		String filePathOut=filePath;
		
		new File(filePath).renameTo(new File(filePathIn));
		
		String wrapline=System.getProperty("os.name").toLowerCase().toString().contains("windows")?"\r\n":"\n";
		
		BufferedReader reader=new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(filePathIn)),"gbk"));
		BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(new File(filePathOut)),"utf-8"));
		
		String nextLine=null;
		while((nextLine=reader.readLine())!=null){
			writer.write(nextLine+wrapline);
		}
		writer.flush();
		reader.close();
		writer.close();
		new File(filePathIn).deleteOnExit();
	}
}
