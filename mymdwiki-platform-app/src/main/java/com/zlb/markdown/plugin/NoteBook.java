package com.zlb.markdown.plugin;

import java.io.IOException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zlb.markdown.data.tool.FenLei;
import com.zlb.markdown.data.tool.JSONTreeToTreeData;
import com.zlb.markdown.plugin.tool.GetFileTextUtf8;

/**
 * mrakdown笔记本概念/收藏夹
 * @author zhulb
 *
 */
public class NoteBook {
	private NoteBook(){}
	private static NoteBook oneInstance=new NoteBook();
	public static NoteBook getInstance(){
		return oneInstance;
	}
	/**
	 * 
	 * [
	 * 	{'url','name','package'},
	 *  {'C:\adfsadf\adsfadf\adf.md','adf.md','嵌入式/面试/重点知识'},
	 * ]
	 * 
	 * 获取笔记本导航树
	 * @return
	 * 		按照package解析成树形
	 * 		树形数据数组
	 * @throws IOException 
	 */
	public JSONArray getTocTree(String filePath) throws IOException{
		GetFileTextUtf8 getFileText=new GetFileTextUtf8();
		String text=getFileText.getText(filePath);
		text=text.substring(text.indexOf("["));
		JSONArray dataArray=JSONArray.parseArray(text);
		JSONArray tocIndex=new JSONArray();
		JSONArray head=dataArray.getJSONArray(0);
		int id=0;
		for(int i=1;i<dataArray.size();i++){
			JSONObject curObj=new JSONObject();
			JSONArray curJSONArray=dataArray.getJSONArray(i);
			for(int j=0;j<head.size();j++){
				curObj.put(head.getString(j),curJSONArray.getString(j));
			}
			curObj.put("id", id);
			curObj.put("pId", id);
			id++;
			tocIndex.add(curObj);
		}
		
		JSONArray fenLeiResult=FenLei.getInstance().toTree(tocIndex);
		
		JSONArray result=JSONTreeToTreeData.getInstance().toTreeData(fenLeiResult);
		return result;
	}
	/**
	 * 
	 * [
	 * 	{'url','name','package'},
	 *  {'C:\adfsadf\adsfadf\adf.md','adf.md','嵌入式/面试/重点知识'},
	 * ]
	 * 
	 * 获取笔记本导航树
	 * @return
	 * 		直接返回原来的顺序数组
	 * 		树形数据数组
	 * @throws IOException 
	 */
	public JSONArray getTocIndex(String filePath) throws IOException{
		GetFileTextUtf8 getFileText=new GetFileTextUtf8();
		String text=getFileText.getText(filePath);
		text=text.substring(text.indexOf("["));
		JSONArray dataArray=JSONArray.parseArray(text);
		JSONArray result=new JSONArray();
		JSONArray head=dataArray.getJSONArray(0);
		int id=0;
		for(int i=1;i<dataArray.size();i++){
			JSONObject curObj=new JSONObject();
			JSONArray curJSONArray=dataArray.getJSONArray(i);
			for(int j=0;j<head.size();j++){
				curObj.put(head.getString(j),curJSONArray.getString(j));
			}
			curObj.put("id", id);
			curObj.put("pId", id);
			id++;
			result.add(curObj);
		}
		return result;
	}
	public static void main(String[] args) throws IOException{
		NoteBook noteBook=NoteBook.getInstance();
		JSONArray tocTree=noteBook.getTocTree("C:/mymdwiki/mymdwiki/src/com/zlb/markdown/plugin/NoteBook.nb");
		JSONArray tocIndex=noteBook.getTocIndex("C:/mymdwiki/mymdwiki/src/com/zlb/markdown/plugin/NoteBook.nb");
		System.out.println("---test---");
	}
}
