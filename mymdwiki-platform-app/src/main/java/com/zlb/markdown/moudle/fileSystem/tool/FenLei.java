package com.zlb.markdown.moudle.fileSystem.tool;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 分类工具
 * @author zhulb
 *
 */
public class FenLei {
	public static String IS_PACKAGE="isPackage";
	public static String PACKAGE_NAME="name";

	private FenLei(){};
	private static FenLei oneInstance=new FenLei();
	public static FenLei getInstance(){
		return oneInstance;
	}
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public JSONArray toTree2(JSONArray data){
		JSONObject result=new JSONObject();
		for(int i=0;i<data.size();i++){
			String packageInfo=data.getJSONObject(i).getString("package");
			String[] packageInfoList=packageInfo.split("/");
			JSONObject cur=result;
			
			//添加包各个包节点
			for(int j=0;j<packageInfoList.length;j++){
				String curPackageName=packageInfoList[j];
				
				JSONArray children=cur.getJSONArray("children");//当前包节点父节点的children列表
				
				if(children==null){//不存在则创建
					children=new JSONArray();
					cur.put("children", children);
				}
				
				JSONObject curPackageObject=null;
				
				boolean isExists=false;
				for(int k=0;k<children.size();k++){
					isExists=curPackageName.equals(children.getJSONObject(k).get(PACKAGE_NAME));//pn表示packageName
					if(isExists){//1.如果包节点存在
						curPackageObject=children.getJSONObject(k);
						break;
					}
				}
				if(!isExists){//2.包节点不存在则创建
					JSONObject newPackage=new JSONObject();
					newPackage.put(PACKAGE_NAME, curPackageName);
					newPackage.put(IS_PACKAGE, "1");//包类型
					children.add(newPackage);
					curPackageObject=newPackage;
				}
				cur=curPackageObject;
				
			}
			
			//添加数据节点
			JSONObject dataJSONObject=data.getJSONObject(i);
			dataJSONObject.put(IS_PACKAGE, "0");//数据节点类型
			JSONArray children=cur.getJSONArray("children");
			if(null==children){
				children=new JSONArray();
				cur.put("children", children);
			}
			children.add(dataJSONObject);
		}
		return result.getJSONArray("children");
	}
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public JSONArray toTree(JSONArray data){
		JSONArray result=new JSONArray();
		//一.外层data循环 把每一个元素都解析成包节点和数据节点的树形结构
		//[['package':'数学/微积分'],[...],[]]
		for(int i=0;i<data.size();i++){
			String packageNames=data.getJSONObject(i).getString("package");
			if(packageNames==null)continue;
			String[] packageNameList=packageNames.split("/");
			JSONObject cur=null;//需要添加的第j个包节点的父对象
			//1.1 内层package循环---逐个添加分类包节点
			//['数学','微积分']
			for(int j=0;j<packageNameList.length;j++){
				String packageName=packageNameList[j];
				//1.1.1 第一个包(最上层的包)是否已经存在，不存在则创建
				if(j==0){//获取第一个包的对象，没有则创建
					boolean isFirstPackageExists=false;
					for(int k=0;k<result.size();k++){
						isFirstPackageExists=packageName.equals(result.getJSONObject(k).getString(PACKAGE_NAME));
						if(isFirstPackageExists){
							cur=result.getJSONObject(k);
							break;
						}
					}
					if(!isFirstPackageExists){
						cur=new JSONObject();
						cur.put(IS_PACKAGE, 1);
						cur.put(PACKAGE_NAME, packageName);
						result.add(cur);
					}
					continue;
				}
				//1.1.2 后续包是否存在，不存在则创建
				else{
					JSONArray children=cur.getJSONArray("children");
					//1.1.2.1 如果父包没有children
					if(children==null){
						JSONObject preCur=cur;
						cur=new JSONObject();
						children=new JSONArray();
						children.add(cur);
						preCur.put("children", children);
						cur.put(IS_PACKAGE,1);
						cur.put(PACKAGE_NAME, packageName);
					}
					//1.1.2.2 父包有children
					else{
						boolean isPackageExists=false;
						for(int k=0;k<children.size();k++){
							isPackageExists=packageName.equals(children.getJSONObject(k).getString(PACKAGE_NAME));
							if(isPackageExists){
								cur=children.getJSONObject(k);
								break;
							}
						}
						if(!isPackageExists){
							JSONObject preCur=cur;
							cur=new JSONObject();
							cur.put(IS_PACKAGE, 1);
							cur.put(PACKAGE_NAME, packageName);
							preCur.getJSONArray("children").add(cur);
						}
					}
				}
			}
			//1.2 添加数据节点
			JSONObject dataJSONObject=data.getJSONObject(i);
			dataJSONObject.put(IS_PACKAGE, "0");//数据节点类型
			JSONArray children=cur.getJSONArray("children");
			if(null==children){
				children=new JSONArray();
				cur.put("children", children);
			}
			children.add(dataJSONObject);
		}
		return result;
	}
	
	public static void main(String args[]){
		JSONArray data=new JSONArray();
		String[] packages=new String[]{"数学/微积分","数学/数论","嵌入式/单片机","音乐/欧美/美国","体育"};
		for(int i=0;i<4;i++){
			JSONObject cur=new JSONObject();
			cur.put("package", packages[i]);
			cur.put("index", i);
			data.add(cur);
		}
		JSONArray result=FenLei.getInstance().toTree(data);
		System.out.println(result.toJSONString());
		
		JSONTreeToTreeData jsonTreeToTreeData=JSONTreeToTreeData.getInstance();
		JSONArray treeData=jsonTreeToTreeData.toTreeData(result);
		System.out.println(treeData.toJSONString());
		
		JSONArray treeData2=jsonTreeToTreeData.toTreeData(result.getJSONObject(0));
		System.out.println(treeData2.toJSONString());
		System.out.println("---end---");
	}
}
