package com.zlb.markdown.moudle.fileSystem.tool;

import java.util.LinkedList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author zhulb
 *
 */
public class JSONTreeToTreeData {
	private JSONTreeToTreeData(){};
	private static JSONTreeToTreeData oneInstance=new JSONTreeToTreeData();
	public static JSONTreeToTreeData getInstance(){
		return oneInstance;
	}
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public JSONArray toTreeData(JSONObject obj){
		int id=0;
		JSONArray result=new JSONArray();
		if(null==obj)return result;
		LinkedList<JSONObject> target=new LinkedList();
		target.add(obj);
		int curProcessing=0;
		int targetSize=target.size();
		while(curProcessing<targetSize){
			JSONObject addObj=(JSONObject)target.get(curProcessing).clone();
			addObj.put("id", id);
			
			JSONArray children=target.get(curProcessing).getJSONArray("children");
			if(children!=null){
				for(int i=0;i<children.size();i++){
					children.getJSONObject(i).put("pId", id);
					target.add(children.getJSONObject(i));
				}
			}
			addObj.remove("children");
			result.add(addObj);
			id++;
			curProcessing++;
			targetSize=target.size();
		}
		
		return result;
	}
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public JSONArray toTreeData(JSONArray obj){
		int id=0;
		JSONArray result=new JSONArray();
		if(null==obj)return result;
		LinkedList<JSONArray> target=new LinkedList();
		target.add(obj);
		int curProcessing=0;
		int targetSize=target.size();
		while(curProcessing<targetSize){
			JSONArray addObj=(JSONArray)((JSONArray)target.get(curProcessing)).clone();
			for(int i=0;i<addObj.size();i++){
				JSONObject curAddObj=(JSONObject) addObj.getJSONObject(i).clone();
				curAddObj.put("id", id);
				if(curProcessing==0)curAddObj.put("pId",id);
				result.add(curAddObj);
				JSONArray curAddObjChildren=curAddObj.getJSONArray("children");
				if(null!=curAddObjChildren){
					target.add(curAddObjChildren);
					for(int j=0;j<curAddObjChildren.size();j++){
						curAddObjChildren.getJSONObject(j).put("pId", id);
					}
				}
				curAddObj.remove("children");
				id++;
			}
			curProcessing++;
			targetSize=target.size();
		}
		return result;
	}
}
