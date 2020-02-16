package com.zlb.markdown.moudle;

import com.alibaba.fastjson.JSONObject;
import com.zlb.markdown.moudle.fileSystem.tool.PathTool;

public abstract class GetDataFactory {
	private static Object initLock=new Object();
	
	public abstract GetFileSource createFileSource();
	
	public abstract GetIndexTree createGetFenLeiIndexTree();
	
	public abstract GetIndexTree createGetTagIndexTree();
	
	public abstract GetIndexTree createGetDirectoryIndexTree();
	
	private static GetDataFactory factory=null;
	
	private static String className="com.zlb.markdown.moudle.fileSystem.GetFileSystemDataFactory";  
	
	public static GetDataFactory getInstance(){
		if(null==factory){
			synchronized (initLock) {
				try {
					Class c=Class.forName(className);
					factory=(GetDataFactory) c.newInstance();
				} catch (Exception e) {
					return null;
				}
			}
		}
		return factory;
	}
	public static void main(String[] args){
		// TODO 开发测试
		GetDataFactory factory=GetDataFactory.getInstance();
		GetIndexTree directoryIndexTree=factory.createGetDirectoryIndexTree();
		JSONObject jObject=(JSONObject) directoryIndexTree.getIndexTree();
	}
}
