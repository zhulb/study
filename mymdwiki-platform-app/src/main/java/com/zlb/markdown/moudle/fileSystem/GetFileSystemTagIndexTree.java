package com.zlb.markdown.moudle.fileSystem;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zlb.markdown.moudle.GetIndexTree;
import com.zlb.markdown.moudle.fileSystem.tool.FenLei;
import com.zlb.markdown.moudle.fileSystem.tool.JSONTreeToTreeData;
import com.zlb.markdown.moudle.fileSystem.tool.Source;

public class GetFileSystemTagIndexTree implements GetIndexTree {

	private String target;

	public JSONArray getIndexTree() {
		JSONArray mdInfo=(JSONArray)Source.getInstance().getSource("mdInfo", Source.choice_save, Source.choice_update);
		JSONArray mdInfoTag=new JSONArray();
		
		JSONArray defaultTags=new JSONArray();
		JSONArray defaultTitles=new JSONArray();
		defaultTags.add("no Tag");
		defaultTitles.add("无标题");
		
		for(int i=0;i<mdInfo.size();i++){
			JSONArray tags=mdInfo.getJSONObject(i).getJSONArray("tag");
			JSONArray titles=mdInfo.getJSONObject(i).getJSONArray("title");
			tags=tags==null?defaultTags:tags;
			titles=titles==null?defaultTitles:titles;
			for(int j=0;j<tags.size();j++){
				for(int k=0;k<titles.size();k++){
					JSONObject addObject=(JSONObject) mdInfo.getJSONObject(i).clone();
					addObject.put("package", tags.getString(j));
					addObject.put("name", titles.getString(k));
					mdInfoTag.add(addObject);
				}
			}
		}
		FenLei fenLei=FenLei.getInstance();
		JSONArray fenLeiResult=fenLei.toTree(mdInfoTag);
		JSONArray resultArray=JSONTreeToTreeData.getInstance().toTreeData(fenLeiResult);
		return resultArray;
	}

	public void setTarget(String target) {
		this.target=target;
	}

}
