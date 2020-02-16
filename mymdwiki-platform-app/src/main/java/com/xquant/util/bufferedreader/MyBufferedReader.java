package com.xquant.util.bufferedreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public abstract class MyBufferedReader {
	
	private BufferedReader bufferedReader;
	private String filePath;
	private String charset;
	
	protected abstract void dealLine(String filePath,String charset,int lineNumber,String lineValue);
	
	public MyBufferedReader(String filePath,String charset) throws UnsupportedEncodingException, FileNotFoundException{
			this.filePath=filePath;
			this.charset=charset;
			this.bufferedReader=new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)),charset));
	}
	
	public MyBufferedReader(File file,String charset) throws UnsupportedEncodingException, FileNotFoundException {
		this.filePath=file.getAbsolutePath();
		this.charset=charset;
		this.bufferedReader=new BufferedReader(new InputStreamReader(new FileInputStream(file),charset));
	}
	
	public MyBufferedReader(Reader in){
		this.bufferedReader=new BufferedReader(in);
	}
	
	public void read() throws IOException{
		int lineNumber=0;
		String lineValue=null;
		while((lineValue=bufferedReader.readLine())!=null){
			dealLine(filePath,charset,lineNumber, lineValue);
		}
	}
	
	public void close() throws IOException{
		this.bufferedReader.close();
	}

	public BufferedReader getBufferedReader() {
		return bufferedReader;
	}

	public void setBufferedReader(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
	
}
