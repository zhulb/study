package com.zlb.markdown.moudle.fileSystem.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class WriteTextFileUtf8 {
	public boolean write(String text,String path) throws IOException {
		byte[] bytes=text.getBytes("utf-8");
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream=new FileOutputStream(new File(path));
			fileOutputStream.write(bytes);
			fileOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fileOutputStream!=null){
				fileOutputStream.close();
			}
		}
		return true;
	}
	public static void main(String[] args) throws IOException{
		new WriteTextFileUtf8().write("好戏开始了['a','adfads','b','afdasfasdf']", "D:\\测试.txt");
	}
}
