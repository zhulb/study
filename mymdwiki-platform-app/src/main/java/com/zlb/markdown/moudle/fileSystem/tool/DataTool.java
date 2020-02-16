package com.zlb.markdown.moudle.fileSystem.tool;

import java.io.File;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class DataTool {
	
	private String path=null;
	private String headInfo=null;
	private JSONArray emptyJsonArray=new JSONArray();
	//提供两个实例，也只有这两个实例可以用 
	private static DataTool dataTool=null;
	private static DataTool dataTool_new=null;
	private DataTool(){
		super();
	}
	public static DataTool getDataTool(){
		if(null == dataTool){
			dataTool=new DataTool();
			File classFile=new File(dataTool.getClass().getClassLoader().getResource("").getPath());
			File topFile=classFile.getParentFile().getParentFile();
			dataTool.path=topFile.getAbsolutePath()+"/md";
			System.out.println(dataTool.path);
		}
		return dataTool;
	}
	public static DataTool getDataToolNew(){
		if(null==dataTool_new){
			dataTool_new=new DataTool();
		}
		return dataTool_new;
	}
	public String getHeadInfo(){
		if(null==this.headInfo){
			System.out.println("loading...");
			this.headInfo=this.getFolderDir(this.path, "/", "/").toString();
		}
		System.out.println("return...");
		return this.headInfo;
	}
	
	public String updateHeadInfo(){
		System.out.println("loading...");
		this.headInfo=this.getFolderDir(this.path, "/", "/").toString();
		System.out.println("return...");
		return this.headInfo;
	}
	
	private JSONObject getFolderDir(String path,String showPath,String folderName){
		
		JSONObject result=new JSONObject();
		JSONArray mdlist=new JSONArray();
		JSONArray folderlist=new JSONArray();
		result.put("folderlist", folderlist);
		result.put("mdlist", mdlist);
		result.put("path", showPath);
		result.put("folderName", folderName);
		
		
		File file=new File(path);
		File[] childFiles=file.listFiles();
		
		JSONObject add=null;
		for(File child:childFiles){
			int type=child.isFile()?1:0;//是不是文件
			String name=child.getName();
			if(1==type){//文件
				if(name.endsWith(".txt")||name.endsWith(".md")){
					add=new JSONObject();
					add.put("name", name);
					add.put("headerlist", emptyJsonArray);
					mdlist.add(add);
				}
			}
			else {//文件夹
				add=this.getFolderDir(child.getAbsolutePath(),"/".equals(showPath)?("/"+name):(showPath+"/"+name),name);
				folderlist.add(add);
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		DataTool dataTool=DataTool.getDataTool();
		File classFile=new File(dataTool.getClass().getClassLoader().getResource("").getPath());
		System.out.println(classFile.getParentFile().getParentFile().getAbsolutePath());
		if(true)return;
		System.out.println(dataTool.getHeadInfo());
	}
	public void setPath(String path) {
		this.path=path;
	}
}
