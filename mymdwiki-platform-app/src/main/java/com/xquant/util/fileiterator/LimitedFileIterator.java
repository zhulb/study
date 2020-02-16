package com.xquant.util.fileiterator;

import java.io.File;
import java.util.LinkedList;
/**
 * 
 * @author zhulb
 *
 */
public class LimitedFileIterator{
	
	public static void main(String[] args) {
		LimitedFileIterator limitedFileIterator=new LimitedFileIterator();
		limitedFileIterator.add("D:\\");
		System.out.println("start");
		while(limitedFileIterator.hasNext()){
			System.out.println(limitedFileIterator.next());
		}
	}
	
	public LimitedFileIterator(){
		super();
	}

	private LinkedList<String> dirs=new LinkedList<String>();
	
	private String[] children;
	
	private int childrenCount;
	
	private int nextChild;
	
	private String parentPath;
	
	public boolean hasNext() {
		while(nextChild>=childrenCount&&dirs.size()>0){
			parentPath=dirs.get(0);
			dirs.remove(0);
			children=new File(parentPath).list();
			nextChild=0;
			childrenCount=children==null?0:children.length;
		}
		return nextChild<childrenCount;
	}

	public String next() {
		String result=parentPath+File.separator+children[nextChild++];
		if(new File(result).isDirectory()){
			dirs.add(result);
		}
		return result;
	}

	public void add(String filePath) {
		dirs.add(filePath);
	}

}
