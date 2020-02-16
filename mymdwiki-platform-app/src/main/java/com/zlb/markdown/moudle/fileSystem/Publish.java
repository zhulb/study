package com.zlb.markdown.moudle.fileSystem;

import java.io.File;

import com.alibaba.fastjson.JSONArray;
import com.zlb.markdown.data.tool.DataTool;
import com.zlb.markdown.moudle.GetDataFactory;
import com.zlb.markdown.moudle.fileSystem.tool.Source;
import com.zlb.markdown.moudle.fileSystem.tool.WriteTextFileUtf8;
/**
 * 文件系统下发布工具
 * @author zhulb
 *
 */
public class Publish {
	private static Object sync = new Object();
	public static String picturePath = "";
	public static String mdDirPath = "D:\\app_workspace\\github_project\\note\\md";
	
	private static boolean isSetMdDir = false;
	
	public static void main(String[] args){
		System.out.println("---startup---");
		String classesPath=Publish.class.getClassLoader().getResource("").getPath();
		System.out.println("classes文件路径:"+classesPath);
		//生成导航文件
		GetDataFactory getDataFactory = GetDataFactory.getInstance();
		if(!isSetMdDir){
			synchronized (sync) {
				((GetFileSystemDataFactory)getDataFactory).updateTarget(mdDirPath);
				isSetMdDir = true;
			}
		}
		
		JSONArray directoryIndexTree=(JSONArray) Source.getInstance().getSource("directoryIndexTree_",Source.choice_save,Source.choice_update);
		JSONArray fenLeiIndexTree=(JSONArray) Source.getInstance().getSource("fenLeiIndexTree",Source.choice_save,Source.choice_update);
		JSONArray tagIndexTree=(JSONArray) Source.getInstance().getSource("tagIndexTree",Source.choice_save,Source.choice_update);
		String headInfo=DataTool.getDataTool().getHeadInfo();
		try{
			WriteTextFileUtf8 writeTextFileUtf8=new WriteTextFileUtf8();
			writeTextFileUtf8.write(directoryIndexTree.toJSONString(), mdDirPath+"/directoryIndexTree.json");
			writeTextFileUtf8.write(fenLeiIndexTree.toJSONString(),  mdDirPath+"/fenLeiIndexTree.json");
			writeTextFileUtf8.write(tagIndexTree.toJSONString(),  mdDirPath+"/tagIndexTree.json");
			writeTextFileUtf8.write(headInfo,  mdDirPath+"/mdhead.json");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
