package com.xquant.object.tablehead;

import java.util.ArrayList;
import java.util.List;

import com.xquant.object.MyTreeNode;

public class TableHeader implements ITableHeader{
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		TableHeaderTree tableHeaderTree=new TableHeaderTree();
		tableHeaderTree.setNodeBindObjType(TableHeader.class);
		boolean addAtLast=true;
		
		List<Object> mergeA=new ArrayList<Object>();
		mergeA.add("A");
		mergeA.add("C");
		
		TableHeaderTreeLeafWithoutId leafA1=new TableHeaderTreeLeafWithoutId();
		TableHeader tableHeaderA0=new TableHeader();
		tableHeaderA0.setHeader("A1");
		leafA1.setTableHeader(tableHeaderA0);
		leafA1.setMerge(mergeA);
		leafA1.setAddAtLast(addAtLast);
		
		TableHeaderTreeLeafWithoutId leafA2=new TableHeaderTreeLeafWithoutId();
		TableHeader tableHeaderA1=new TableHeader();
		tableHeaderA1.setHeader("A2");
		leafA2.setTableHeader(tableHeaderA1);
		leafA2.setMerge(mergeA);
		leafA2.setAddAtLast(addAtLast);
		
		TableHeaderTreeLeafWithoutId leafA3=new TableHeaderTreeLeafWithoutId();
		TableHeader tableHeaderA3=new TableHeader();
		tableHeaderA3.setHeader("A3");
		leafA3.setTableHeader(tableHeaderA3);
		leafA3.setMerge(mergeA);
		leafA3.setAddAtLast(addAtLast);
		
		TableHeaderTreeLeafWithoutId leafA4=new TableHeaderTreeLeafWithoutId();
		TableHeader tableHeaderA4=new TableHeader();
		tableHeaderA4.setHeader("A4");
		leafA4.setTableHeader(tableHeaderA4);
		leafA4.setMerge(mergeA);
		leafA4.setAddAtLast(addAtLast);

		List<Object> mergeB=new ArrayList<Object>();
		mergeB.add("B");
		
		TableHeaderTreeLeafWithoutId leafB1=new TableHeaderTreeLeafWithoutId();
		TableHeader tableHeaderB1=new TableHeader();
		tableHeaderB1.setHeader("B1");
		leafB1.setTableHeader(tableHeaderB1);
		leafB1.setMerge(mergeB);
		leafB1.setAddAtLast(addAtLast);

		TableHeaderTreeLeafWithoutId leafB2=new TableHeaderTreeLeafWithoutId();
		TableHeader tableHeaderB2=new TableHeader();
		tableHeaderB2.setHeader("B2");
		leafB2.setTableHeader(tableHeaderB2);
		leafB2.setMerge(mergeB);
		leafB2.setAddAtLast(addAtLast);
		
		tableHeaderTree.addLeaf(leafA1);
		tableHeaderTree.addLeaf(leafA2);
		
		tableHeaderTree.addLeaf(leafB1);		
		tableHeaderTree.addLeaf(leafB2);
		
		tableHeaderTree.addLeaf(leafA3);
		
		tableHeaderTree.addLeaf(leafA4);
		
		//遍历
		tableHeaderTree.clear();
		while(tableHeaderTree.hasNext()) {
			MyTreeNode next=tableHeaderTree.next();
			System.out.println(next.getNodeId());
		}
		
		System.out.println("f");
	}
	
	private String header;
	
	private boolean isLastHeader=false;
	
	private String filePath;
	
	private int index;
	
	public boolean isLastHeader() {
		return isLastHeader;
	}

	public void setLastHeader(boolean isLastHeader) {
		this.isLastHeader = isLastHeader;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	@Override
	public boolean check(Object mergeParentInfo) {
		return header.equals(mergeParentInfo.toString());
	}


	@Override
	public void init(Object mergeParentInfo) {
		this.header=mergeParentInfo.toString();
	}

}