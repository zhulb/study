package com.xquant.object.tablehead;

import java.util.ArrayList;
import java.util.List;

import com.xquant.object.MyTree;
import com.xquant.object.MyTreeNode;

/**
 * 不指定id，树的叶节点
 * @author zhulb
 *
 */
public class TableHeaderTreeLeafWithoutId {

	private List<Object> merge;
	
	private ITableHeader tableHeader;
	
	private int iteratorPoint=0;
	
	private List<MyTreeNode> curParentChildren;
	
	private int curParentChildrenPoint=0;
	
	/**
	 * true:
	 * 	表头合并，只和最后一个表头进行比较
	 * 	可生成如下表头
	 * 			          Root
	 * 		A		|		B		|		A		|
	 * 	A1		A2	|	B1		B2	|	A3		A4	|
	 * 
	 * false:
	 * 	表头合并：和之的表头也会进行比较 
	 * 上面表头会变成
	 * 					  Root
	 * 			A			|		B
	 * A1 |	A2	|  A3 | A4	|	B1		B2
	 */
	private boolean addAtLast=true;
	
	public boolean hasNextParent() {
		return !(merge==null||iteratorPoint >= merge.size());
	}

	public Object nextParent() {
		return merge.get(iteratorPoint++);
	}

	public MyTreeNode createParent(MyTreeNode curParent, Object curCheckParent, String nextId, Class nodeBindObjType) throws InstantiationException, IllegalAccessException {
		List<MyTreeNode> children=curParent.getChildren();
		if(children==null) {
			children=new ArrayList<MyTreeNode>();
			curParent.setChildren(children);
		}
		MyTreeNode newMyTreeNode=new MyTreeNode();
		newMyTreeNode.setNodeId(nextId);
		newMyTreeNode.setParent(curParent);
		children.add(newMyTreeNode);
		
		ITableHeader newTableHeader=(ITableHeader) nodeBindObjType.newInstance();
		newTableHeader.init(curCheckParent);
		newMyTreeNode.setBindObject(newTableHeader);
		return newMyTreeNode;
	}
	

	public MyTreeNode createLeafNode(MyTreeNode curParent, TableHeaderTreeLeafWithoutId leaf, String nextId) {
		List<MyTreeNode> children=curParent.getChildren();
		if(children==null) {
			children=new ArrayList<MyTreeNode>();
			curParent.setChildren(children);
		}
		MyTreeNode newMyTreeNode=new MyTreeNode();
		newMyTreeNode.setNodeId(nextId);
		newMyTreeNode.setParent(curParent);
		children.add(newMyTreeNode);
		
		newMyTreeNode.setBindObject(leaf.getTableHeader());
		return newMyTreeNode;
	}
		

	public boolean check(MyTreeNode myTreeNode, Object curCheckParent) {
		ITableHeader tableHeader = (ITableHeader) myTreeNode.getBindObject();
		return tableHeader.check(curCheckParent);
	}

	public void setCurParentChildren(List<MyTreeNode> children) {
		this.curParentChildren=children;
		this.curParentChildrenPoint=addAtLast?children.size()-1:0;
	}

	public boolean hasNextParentChild() {
		return this.curParentChildrenPoint<this.curParentChildren.size();
	}

	public MyTreeNode nextParentChild() {
		return this.curParentChildren.get(this.curParentChildrenPoint++);
	}

	public List<Object> getMerge() {
		return merge;
	}

	public void setMerge(List<Object> merge) {
		this.merge = merge;
	}

	public ITableHeader getTableHeader() {
		return tableHeader;
	}

	public void setTableHeader(ITableHeader tableHeader) {
		this.tableHeader = tableHeader;
	}

	public int getIteratorPoint() {
		return iteratorPoint;
	}

	public void setIteratorPoint(int iteratorPoint) {
		this.iteratorPoint = iteratorPoint;
	}

	public int getCurParentChildrenPoint() {
		return curParentChildrenPoint;
	}

	public void setCurParentChildrenPoint(int curParentChildrenPoint) {
		this.curParentChildrenPoint = curParentChildrenPoint;
	}

	public boolean isAddAtLast() {
		return addAtLast;
	}

	public void setAddAtLast(boolean addAtLast) {
		this.addAtLast = addAtLast;
	}

	public List<MyTreeNode> getCurParentChildren() {
		return curParentChildren;
	}
}
