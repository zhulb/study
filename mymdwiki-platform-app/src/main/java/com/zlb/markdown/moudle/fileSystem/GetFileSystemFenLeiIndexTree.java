package com.zlb.markdown.moudle.fileSystem;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zlb.markdown.moudle.GetIndexTree;
import com.zlb.markdown.moudle.fileSystem.tool.FenLei;
import com.zlb.markdown.moudle.fileSystem.tool.GetDir;
import com.zlb.markdown.moudle.fileSystem.tool.JSONTreeToTreeData;
import com.zlb.markdown.moudle.fileSystem.tool.Source;

public class GetFileSystemFenLeiIndexTree implements GetIndexTree{

	private String target;

	public JSONArray getIndexTree() {
		JSONArray mdInfo=(JSONArray)Source.getInstance().getSource("mdInfo", Source.choice_save, Source.choice_update);
		
		JSONArray defaultPakages=new JSONArray();
		defaultPakages.add("无分类信息");
		JSONArray defaultTitle=new JSONArray();
		defaultTitle.add("无标题");
		
		JSONArray mdInfoFneLei=new JSONArray();
		for(int i=0;i<mdInfo.size();i++){
			JSONArray packages=mdInfo.getJSONObject(i).getJSONArray("package");
			JSONArray titles=mdInfo.getJSONObject(i).getJSONArray("title");
			packages=packages==null?defaultPakages:packages;
			titles=titles==null?defaultTitle:titles;
			for(int j=0;j<packages.size();j++){
				for(int k=0;k<titles.size();k++){
					JSONObject addObject=(JSONObject) mdInfo.getJSONObject(i).clone();
					addObject.put("package",packages.getString(j));
					addObject.put("name", titles.getString(k));
					mdInfoFneLei.add(addObject);
				}
			}
		}
		FenLei fenLei=FenLei.getInstance();
		JSONArray result=fenLei.toTree(mdInfoFneLei);
		
		JSONArray returnResult=JSONTreeToTreeData.getInstance().toTreeData(result);
		for(int i=0;i<returnResult.size();i++){
			if(returnResult.getJSONObject(i).getString(FenLei.IS_PACKAGE).equals("1")){
				returnResult.getJSONObject(i).put("name", returnResult.getJSONObject(i).getString(FenLei.PACKAGE_NAME));
			}
		}
		return returnResult;
	}

	public void setTarget(String target) {
		this.target=target;
	}

}
