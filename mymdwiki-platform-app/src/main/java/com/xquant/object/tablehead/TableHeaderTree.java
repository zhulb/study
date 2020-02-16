package com.xquant.object.tablehead;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.xquant.object.MyTree;
import com.xquant.object.MyTreeNode;

/**
 * 表头树
 * @author zhulb
 *
 */
public class TableHeaderTree extends MyTree{
	
	
	private int totalCount=0;
	
	private int nextId=0;
	
	private Class nodeBindObjType;
	
	public String getNextId() {
		return String.valueOf((nextId++));
	}
	
	/**
	 * 新增尾节点
	 * @param leaf
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void addLeaf(TableHeaderTreeLeafWithoutId leaf) throws InstantiationException, IllegalAccessException {
		this.totalCount=this.totalCount+1;
		if(this.getRoot()==null)init();
		MyTreeNode curParent=this.getRoot();
		Object curCheckParent=null;
		String nextId = null;
		while(leaf.hasNextParent()) {
			curCheckParent=leaf.nextParent();
			//获取下一个curParent
			List<MyTreeNode> children=curParent.getChildren();
			if(children==null||children.size()==0) {
				nextId=getNextId();
				curParent=leaf.createParent(curParent,curCheckParent,nextId,this.getNodeBindObjType());
			} else {
				boolean checkResult=false;
				leaf.setCurParentChildren(children);
				while(leaf.hasNextParentChild()) {
					MyTreeNode nextParentChild=leaf.nextParentChild();
					checkResult=leaf.check(nextParentChild, curCheckParent);
					if(checkResult) {
						curParent=nextParentChild;
						break;
					}
				}
				if(!checkResult) {
					nextId=getNextId();
					curParent=leaf.createParent(curParent,curCheckParent,nextId,this.getNodeBindObjType());
				}
			}
		}
		//创建最后一个节点
		nextId=getNextId();
		leaf.createLeafNode(curParent,leaf,nextId);
	}

	private void init() {
		MyTreeNode root=new MyTreeNode();
		root.setNodeId(getNextId());
		this.setRoot(root);
	}

	public void setNodeBindObjType(Class nodeBindObjType) {
		this.nodeBindObjType=nodeBindObjType;
		
	}

	protected Class getNodeBindObjType() {
		return nodeBindObjType;
	}
	
	//迭代器
	
	private MyTreeNode nextNode;
	
	private List<Integer> parentNextNodeIndexs;
	
	public void clear() {
		parentNextNodeIndexs=new LinkedList<Integer>();
		nextNode=this.getRoot();
		
	}
	
	@Override
	public boolean hasNext() {
		return nextNode!=null;
	}

	@Override
	public MyTreeNode next() {
		MyTreeNode result=nextNode;
		updateNextNode();
		return result;
	}

	private void updateNextNode() {
		//有子节点
		List<MyTreeNode> children=nextNode.getChildren();
		if(children!=null&&children.size()!=0) {
			this.nextNode=children.get(0);
			parentNextNodeIndexs.add(0, 1);
		} 
		//没有
		else {
			MyTreeNode root=this.getRoot();
			while(nextNode!=root) {
				int parentNextNodeIndex=parentNextNodeIndexs.get(0);
				MyTreeNode parent=nextNode.getParent();
				children=parent.getChildren();
				if(nextNode.getParent().getChildren().size()>parentNextNodeIndex) {//当节点有下一个子节点
					nextNode=children.get(parentNextNodeIndex);
					parentNextNodeIndexs.set(0, parentNextNodeIndex+1);
					break;
				} else {//当父节点没有下一个子节点
					nextNode=parent;
					parentNextNodeIndexs.remove(0);
				}
			}
			if(nextNode==root)nextNode=null;
		}
	}
	
	//迭代器 end
	
	public void sortByIsLastLeaf() {
		this.getRoot().sortByIsLastLeaf();
	}

	public static void main(String[] args) {
		List<String> f=new LinkedList<String>();
		f.add("f");
		f.set(1,"EF");
		System.out.println(f.size());
	}


}
