package com.zlb.markdown.data.tool;

import java.io.File;
import java.util.LinkedList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 获取目录结构
 * @author zhulb
 *
 */
public class GetDir {
	public static String IS_DIR="isDir";
	public static String FILE_PATH="url";
	/**
	 * 不允许外部类创建对象
	 */
	private GetDir(){};
	/**
	 * 单例对象
	 */
	private static GetDir oneObject = new GetDir();
	/**
	 * 获取单例对象
	 * @return
	 */
	public static GetDir getInstance(){
		return oneObject;
	}
	/**
	 * 循环方法获取目录结构
	 * 空文件夹去除children
	 * @param parentPath
	 * @return
	 * {
	 * 	 'type'     :'..',
	 * 	 'url' 		:'..',
	 *   'children'	: [
	 *   	{type url children},
	 *   	{type url children}
	 *   ]
	 * }
	 */
	public JSONObject getDirLoop(String parentPath){
		JSONObject result=new JSONObject();
		JSONArray children=null;
		/*用json对象充当待处理对象可以完美的存储信息
		 * 甚至可以绑定到之前的对象，但是如果绑定之前的对象，要避免toString()时候死循环需要在toString()之前解绑
		 */
		LinkedList<JSONObject> target = new LinkedList();
		target.add(result);
		result.put(IS_DIR,1);//文件夹
		result.put(FILE_PATH, parentPath);
		int cur = 0;
		int size = target.size();
		while(size>cur){//给target链表的每一个文件夹对象添加children属性，并且target链表可以动态追加
			JSONObject curTarget=target.get(cur);
			children=new JSONArray();
			
			File curFile=new File(target.get(cur).getString(FILE_PATH));
			File[] childrenFile=curFile.listFiles();
			int j=0;
			for(j=0;j<childrenFile.length;j++){
				File child=childrenFile[j];
				if(child.isFile()){//文件
					JSONObject childJSONObject=new JSONObject();
					childJSONObject.put(IS_DIR, 0);
					childJSONObject.put(FILE_PATH, child.getAbsolutePath());
					children.add(childJSONObject);
				}
				else{//文件夹
					JSONObject childJSONObject=new JSONObject();
					childJSONObject.put(IS_DIR, 1);
					childJSONObject.put(FILE_PATH, child.getAbsolutePath());
					children.add(childJSONObject);
					target.add(childJSONObject);
				}
			}
			
			if(children.size()>0)curTarget.put("children", children);
			
			cur++;
			size=target.size();
		}
		return result;
	}
	/**
	 * 
	 * @param parentPath
	 * @return
	 * {
	 * 	 'type'     :'..',
	 * 	 'url' 		:'..',
	 *   'children'	: [
	 *   	{type url children},
	 *   	{type url children}
	 *   ]
	 * }
	 */
	public JSONObject getDirDiGui(String parentPath){
		JSONObject result=new JSONObject();
		result.put(IS_DIR, 1);
		result.put(FILE_PATH, parentPath);
		JSONArray children=new JSONArray();
		File cur=new File(parentPath);
		File[] childrenFile=cur.listFiles();
		for(File curChild:childrenFile){
			if(curChild.isFile()){
				JSONObject curChildJSONObject=new JSONObject();
				curChildJSONObject.put(IS_DIR, 0);
				curChildJSONObject.put(FILE_PATH, curChild.getAbsolutePath());
				children.add(curChildJSONObject);
			}
			else{
				JSONObject curChildJSONObject = getDirDiGui(curChild.getAbsolutePath());
				children.add(curChildJSONObject);
			}
		}
		if(children.size()>0)result.put("children", children);
		return result;
	}
	/**
	 * 循环方法获取目录结构，可以选择文件类型
	 * @param parentPath
	 * @param endWith
	 * @return
	 * {
	 * 	 'type'     :'..',
	 * 	 'url' 		:'..',
	 *   'children'	: [
	 *   	{type url children},
	 *   	{type url children}
	 *   ]
	 * }
	 */
	public JSONObject getDirLoop(String parentPath,String[] endWith){
		JSONObject result=new JSONObject();
		JSONArray children=null;
		LinkedList<JSONObject> target = new LinkedList();
		target.add(result);
		result.put(IS_DIR,1);//文件夹
		result.put(FILE_PATH, parentPath);
		int cur = 0;
		int size = target.size();
		while(size>cur){//给target链表的每一个文件夹对象添加children属性，并且target链表可以动态追加
			JSONObject curTarget=target.get(cur);
			children=new JSONArray();
			
			File curFile=new File(target.get(cur).getString(FILE_PATH));
			File[] childrenFile=curFile.listFiles();
			int j=0;
			for(j=0;j<childrenFile.length;j++){
				File child=childrenFile[j];
				String childPath=child.getAbsolutePath();
				if(child.isFile()){//文件
					//先判断是否符合指定类型
					if(null!=endWith&&endWith.length!=0){
						boolean match=false;
						for(int k=0;k<endWith.length;k++){
							match=match||childPath.endsWith(endWith[k]);
						}
						if(!match)continue;
					}
					JSONObject childJSONObject=new JSONObject();
					childJSONObject.put(IS_DIR, 0);
					childJSONObject.put(FILE_PATH, childPath);
					children.add(childJSONObject);
				}
				else{//文件夹
					JSONObject childJSONObject=new JSONObject();
					childJSONObject.put(IS_DIR, 1);
					childJSONObject.put(FILE_PATH, childPath);
					children.add(childJSONObject);
					target.add(childJSONObject);
				}
			}
			
			if(children.size()>0)curTarget.put("children", children);
			
			cur++;
			size=target.size();
		}
		return result;	
	}
	
	public static void main(String args[]){
		JSONObject result2=GetDir.getInstance().getDirLoop("C:\\mymdwiki\\mymdwiki\\src\\com\\zlb\\markdown",null);
		
		JSONArray treeData=JSONTreeToTreeData.getInstance().toTreeData(result2);
		System.out.println(treeData.toJSONString());
		
		System.out.println("---test--");
	}
}
