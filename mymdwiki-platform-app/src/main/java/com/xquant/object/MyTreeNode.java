package com.xquant.object;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.xquant.object.tablehead.ITableHeader;
import com.xquant.object.tablehead.TableHeader;
import com.xquant.object.tablehead.TableHeaderTree;
import com.xquant.util.debug.Debug;
import com.zlb.markdown.moudle.fileSystem.tool.PrintCurStackTrace;

/**
 * 单负节点树节点对象
 * @author zhulb
 *
 */
public class MyTreeNode {
	private String nodeId;
	private MyTreeNode parent;
	private List<MyTreeNode> children;
	private ITableHeader bindObject;
	/**
	 * 获取父节点个数，所在层数
	 * @return
	 */
	public int getParentCount() {
		MyTreeNode treeNode=this;
		int result=0;
		while((treeNode=treeNode.parent)!=null)result++;
		return result;
	}
	
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public MyTreeNode getParent() {
		return parent;
	}

	public void setParent(MyTreeNode parent) {
		this.parent = parent;
	}

	public List<MyTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<MyTreeNode> children) {
		this.children = children;
	}

	public ITableHeader getBindObject() {
		return bindObject;
	}

	public void setBindObject(ITableHeader bindObject) {
		this.bindObject = bindObject;
	}

	public boolean isRoot() {
		return this.parent==null;
	}
	
	public void remove() {
		this.parent.children.remove(this);
	}

	public void sortByIsLastLeaf() {
		List<MyTreeNode> children=this.getChildren();
		if(children!=null&&children.size()!=0) {
			Collections.sort(children,new Comparator<MyTreeNode>() {
				@Override
				public int compare(MyTreeNode arg0, MyTreeNode arg1) {
					TableHeader t0=(TableHeader) arg0.getBindObject();
					TableHeader t1=(TableHeader) arg1.getBindObject();
					//TODO tgs
					if(Debug.printStackTrace)PrintCurStackTrace.println("bookmark放到自己目录下，而不是子目录下");
					if((t0.isLastHeader()&&t1.isLastHeader())
							||!t0.isLastHeader()&&!t1.isLastHeader()) {
						return t0.getHeader().compareTo(t1.getHeader());
					}
					if(t0.isLastHeader())return -1;
					return 1;
				}
			});
			for(int i=0;i<children.size();i++) {
				children.get(i).sortByIsLastLeaf();
			}
		}
	}
	
}
