package com.zlb.markdown.plugin.practise;

import java.io.File;
import java.io.IOException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zlb.markdown.plugin.tool.GetFileTextUtf8;

/**
 * 读取获取练习卷以及历史答卷
 * @author zhulb
 *
 */
public class Practise {
	private Practise(){};
	private static Practise oneInstance=new Practise();
	public static Practise getInstance(){
		return oneInstance;
	}
	public JSONArray getPractise(String questPath) throws IOException{
		File questFile=new File(questPath);
		String questName=questFile.getName().replace(".md", "");
		String questParentFilePath=questFile.getParent();
		JSONArray result=new JSONArray();
		//获取问卷
		String questText=new GetFileTextUtf8().getText(questPath);
		JSONObject questObj=new JSONObject();
		questObj.put("isQuest", 1);
		questObj.put("text", questText);
		result.add(questObj);
		//获取答卷
		int answerIndex=1;
		File answerFile=new File(questParentFilePath+"/"+questName+"_answer_"+answerIndex+".md");
		answerIndex++;
		while(answerFile.exists()){
			String answerText=new GetFileTextUtf8().getText(answerFile.getAbsolutePath());
			JSONObject answerObj=new JSONObject();
			answerObj.put("isQuest", 0);
			answerObj.put("text", answerText);
			result.add(answerObj);
			answerFile=new File(questParentFilePath+"/"+questName+"_answer_"+answerIndex+".md");
			answerIndex++;
		}
		return result;
	}
	public static void main(String[] args) throws IOException{
		Practise instance=Practise.getInstance();
		JSONArray practise=instance.getPractise("C:/mymdwiki/mymdwiki/src/com/zlb/markdown/plugin/practise/问卷_程序测试.md");
		System.out.println("---test---");
	}
}
