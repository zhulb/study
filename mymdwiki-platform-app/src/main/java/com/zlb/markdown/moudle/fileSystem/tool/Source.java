package com.zlb.markdown.moudle.fileSystem.tool;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zlb.markdown.moudle.GetDataFactory;
import com.zlb.markdown.moudle.GetIndexTree;

public class Source {
	public static final int SAVE_SOURCE = 0;
	public static final int NOT_SAVE_SOURCE=1;
	public static final int UPDATE_SOURCE=0;
	public static final int NOT_UPDATE_SOURCE=1;
	public static int choice_update=NOT_UPDATE_SOURCE;
	public static int choice_save=SAVE_SOURCE;
	private JSONObject jObject=new JSONObject();
	private Source(){}
	private static Source oneInstance=null;
	public static Source getInstance(){
		if(oneInstance==null){
			oneInstance=new Source();
		}
		return oneInstance;
	}
	public void setSource(String name,Object target){
		jObject.put(name,target);
	}
	public Object getSource(String name){
		return jObject.get(name);
	}
	
	public Object getSource(String name, int saveSource,int update) {
		Object result=jObject.get(name);
		if(null==result||Source.UPDATE_SOURCE==update){
			GetIndexTree tree=null;
			if("directoryIndexTree".equals(name)){
				tree=GetDataFactory.getInstance().createGetDirectoryIndexTree();
				JSONObject treeObj=(JSONObject) tree.getIndexTree();
				JSONArray resultArray=JSONTreeToTreeData.getInstance().toTreeData(treeObj);
				result=resultArray.clone();
				if(SAVE_SOURCE==saveSource)jObject.put(name, resultArray);
			}
			else if("directoryIndexTree_".equals(name)){//去除_url
				tree=GetDataFactory.getInstance().createGetDirectoryIndexTree();
				JSONObject treeObj=(JSONObject) tree.getIndexTree();
				JSONArray resultArray=JSONTreeToTreeData.getInstance().toTreeData(treeObj);
				JSONArray cloneArray=(JSONArray) resultArray.clone();
				for(int i=0;i<cloneArray.size();i++){
					cloneArray.getJSONObject(i).remove(GetDir.FILE_PATH2);
				}
				result=cloneArray;
				if(SAVE_SOURCE==saveSource)jObject.put(name, resultArray);
			}
			else if("fenLeiIndexTree".equals(name)){
				tree=GetDataFactory.getInstance().createGetFenLeiIndexTree();
				JSONArray resultArray=(JSONArray) tree.getIndexTree();
				result=resultArray;
				if(SAVE_SOURCE==saveSource)jObject.put(name, resultArray);
			}
			else if("tagIndexTree".equals(name)){
				tree=GetDataFactory.getInstance().createGetTagIndexTree();
				JSONArray resultArray=(JSONArray)tree.getIndexTree();
				result=resultArray;
				if(SAVE_SOURCE==saveSource)jObject.put(name, resultArray);
			}
			else if("mdInfo".equals(name)){
				JSONArray mdInfo=new JSONArray();
				JSONArray allFile=(JSONArray) this.getSource("directoryIndexTree",Source.SAVE_SOURCE,Source.NOT_UPDATE_SOURCE);
				for(int i=0;i<allFile.size();i++){
					//当是txt或者md文件时
					JSONObject cur=allFile.getJSONObject(i);
					if(!cur.getBoolean(GetDir.IS_DIR)){
						JSONObject addObject=null;
						if(cur.getString(GetDir.FILE_PATH2).endsWith("md")){
							String urlString=cur.getString(GetDir.FILE_PATH2).replace(".md", ".info");
							addObject=TextFile2Json.toJSON(urlString);
							if(addObject==null)continue;
							if(null!=addObject.getJSONArray("title"))addObject.put("name", addObject.getJSONArray("title").get(0));
							if(addObject!=null){
								addObject.put(GetDir.FILE_PATH, cur.getString(GetDir.FILE_PATH));
								mdInfo.add(addObject);
							}
						}
						else if(cur.getString(GetDir.FILE_PATH2).endsWith("txt")){
							String urlString=cur.getString(GetDir.FILE_PATH2).replace(".txt", ".info");
							addObject=TextFile2Json.toJSON(urlString);
							if(addObject==null)continue;
							if(null!=addObject.getJSONArray("title"))addObject.put("name", addObject.getJSONArray("title").get(0));
							if(addObject!=null){
								addObject.put(GetDir.FILE_PATH, cur.getString(GetDir.FILE_PATH));
								mdInfo.add(addObject);
							}
						}
					}
					
				}
				result=mdInfo;
				if(SAVE_SOURCE==saveSource)jObject.put(name, mdInfo);
			}
		}
		return result;
	}
}
