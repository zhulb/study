package com.zlb.markdown;
import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.alibaba.fastjson.JSONArray;
import com.xquant.util.markdown.EventConfig;
import com.xquant.util.markdown.Markdown;
import com.xquant.util.markdown.indexconfig.CreateIndexMdConfig;
import com.zlb.markdown.data.tool.DataTool;
import com.zlb.markdown.moudle.fileSystem.tool.Source;
import com.zlb.markdown.moudle.fileSystem.tool.WriteTextFileUtf8;

public class AutoRun implements ServletContextListener{

	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("---shutdown---");
		String a="f"; 
		String b=a+"e";
		System.out.println(b);
	}

	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("---startup---");
		String classesPath=this.getClass().getClassLoader().getResource("").getPath();
		System.out.println("classes文件路径:"+classesPath);
		
		//生成导航文件
//		JSONArray directoryIndexTree=(JSONArray) Source.getInstance().getSource("directoryIndexTree_",Source.choice_save,Source.choice_update);
//		JSONArray fenLeiIndexTree=(JSONArray) Source.getInstance().getSource("fenLeiIndexTree",Source.choice_save,Source.choice_update);
//		JSONArray tagIndexTree=(JSONArray) Source.getInstance().getSource("tagIndexTree",Source.choice_save,Source.choice_update);
//		String headInfo=DataTool.getDataTool().getHeadInfo();
//		try{
//			WriteTextFileUtf8 writeTextFileUtf8=new WriteTextFileUtf8();
//			String urlString=(new WriteTextFileUtf8()).getClass().getClassLoader().getResource("").toString();
//			urlString=new File(urlString).getParentFile().getParentFile().getPath();
//			writeTextFileUtf8.write(directoryIndexTree.toJSONString(), urlString.substring(6)+"/md/directoryIndexTree.json");
//			writeTextFileUtf8.write(fenLeiIndexTree.toJSONString(),  urlString.substring(6)+"/md/fenLeiIndexTree.json");
//			writeTextFileUtf8.write(tagIndexTree.toJSONString(),  urlString.substring(6)+"/md/tagIndexTree.json");
//			writeTextFileUtf8.write(headInfo,  urlString.substring(6)+"/md/mdhead.json");
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		//生成导航md
		Markdown create=new Markdown();
		EventConfig createIndexMdConfig=new CreateIndexMdConfig();
		create.addEventConfig(createIndexMdConfig);
		create.add(CreateIndexMdConfig.mdUploadDir);
		try {
			create.loadAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		String urlString=(new WriteTextFileUtf8()).getClass().getClassLoader().getResource("").toString();
		urlString=new File(urlString).getParentFile().getParentFile().getPath();
		System.out.println("---start----");
	}

}
