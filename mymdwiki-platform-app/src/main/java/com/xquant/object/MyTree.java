package com.xquant.object;

public abstract class MyTree {
	//1。树结构定义
	//2。树的创建和维护API
	//3.树的使用API
	//	3.1 遍历树
	//	3.2直接转换为场景需要的树的数据格式
	
	private MyTreeNode root; 
	
	public MyTreeNode getRoot() {
		return this.root;
	}
	
	public void setRoot(MyTreeNode root) {
		this.root=root;
	}
	
	//前序遍历
	public abstract boolean hasNext();
	
	public abstract MyTreeNode next();
	
}
