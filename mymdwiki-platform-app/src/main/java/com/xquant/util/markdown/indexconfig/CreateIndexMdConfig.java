package com.xquant.util.markdown.indexconfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.crypto.URIDereferencer;

import com.xquant.object.MyTreeNode;
import com.xquant.object.tablehead.TableHeader;
import com.xquant.object.tablehead.TableHeaderTree;
import com.xquant.object.tablehead.TableHeaderTreeLeafWithoutId;
import com.xquant.util.bufferedreader.MyBufferedReader;
import com.xquant.util.debug.Debug;
import com.xquant.util.markdown.EventConfig;
import com.xquant.util.markdown.Markdown;
import com.xquant.util.markdown.entity.Bookmark;
import com.xquant.util.markdown.entity.Category;
import com.xquant.util.markdown.entity.Tag;
import com.zlb.markdown.moudle.fileSystem.tool.PrintCurStackTrace;

public class CreateIndexMdConfig implements EventConfig {
	
	public static String mdUploadDir;
	
	public static String indexMdPath;
	
	public static String line;
	
	static {
		mdUploadDir=Thread.currentThread().getContextClassLoader().getResource("").getFile().replace("/WEB-INF/classes/","");
		mdUploadDir=Thread.currentThread().getContextClassLoader().getResource("").getFile().replace("/target/classes/","");
		mdUploadDir=mdUploadDir+File.separator+"src"+File.separator+"main"+File.separator+"webapp"+File.separator+"md";
		mdUploadDir=new File(mdUploadDir).getAbsolutePath();
		
		indexMdPath=mdUploadDir+File.separator+"index.md";
		
		if(System.getProperty("os.name").toLowerCase().contains("window")) {
			line="\r\n";
		} else {
			line="\n";
		}
	}
	
	public static void main(String[] args) throws IOException {
		Markdown create=new Markdown();
		EventConfig createIndexMdConfig=new CreateIndexMdConfig();
		create.addEventConfig(createIndexMdConfig);
		create.add(mdUploadDir);
		create.loadAll();
	}
	
	public static final String categoryRegex="^(.*?@category *)(.*)";
	
	//public static final String tagRegex="^(.*?@tag *)(.*)";
	public static final String tagRegex="^(@tag *)(.*)";
	
	//public static final String bookmarkRegex="^(.*?@bookmark *)(.*)";
	public static final String bookmarkRegex="^(@bookmark *)(.*)";
	
	//public static final String archiveRegex="^(.*?@time *)(.*)";
	public static final String archiveRegex="^(@time *)(.*)";
	
	public static final String headerRegex="^(#+.*? *)(.*)";
	
	public Pattern headerPattern=Pattern.compile(headerRegex);
	
	public Pattern bookmarkPattern=Pattern.compile(bookmarkRegex);
	
	public Pattern archivePattern=Pattern.compile(archiveRegex);
	
	public Pattern categoryPattern=Pattern.compile(categoryRegex);
	
	public Pattern tagPattern=Pattern.compile(tagRegex);
	
	public String markDownDir;
	
	private TableHeaderTree bookmarks=new TableHeaderTree();
	
	private TableHeaderTree categories=new TableHeaderTree();
	
	private TableHeaderTree archives=new TableHeaderTree();
	
	private TableHeaderTree tags=new TableHeaderTree();
	
	private TableHeaderTree headers=new TableHeaderTree();
	
	private TableHeaderTree dirs=new TableHeaderTree();
	
	public CreateIndexMdConfig() {
		this.bookmarks.setNodeBindObjType(TableHeader.class);
		this.tags.setNodeBindObjType(TableHeader.class);
		this.categories.setNodeBindObjType(TableHeader.class);
		this.headers.setNodeBindObjType(TableHeader.class);
		this.archives.setNodeBindObjType(TableHeader.class);
		this.dirs.setNodeBindObjType(TableHeader.class);
	}
	
	@Override
	public String getOutDir() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createOutDir() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void afterReaderAll() {
		try {
			File indexMdFile=new File(indexMdPath);
			String text=this.toString();
			byte[] bytes = text.getBytes("utf-8");
			
			FileOutputStream fileOutputStream=new FileOutputStream(indexMdFile);
			fileOutputStream.write(bytes);
			fileOutputStream.flush();
			fileOutputStream.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private int bookMarkCount;
	private int tagCount;
	private int categoryCount;
	private int headerCount;
	private int archiveCount;
	private int dirCount;

	@Override
	public void onGetNextFile(File file,String charset) {
		try {
			String lastModifyTime=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(file.lastModified()));
			String lineString=null;
			StringBuilder stringBuilder=new StringBuilder();
			bookMarkCount=0;//可以判断是否为0判断文件是否有书签，同时在遍历中，设置书签对应到文中的第几个
			tagCount=0;//可以通过是否为0判断文件是否标签
			categoryCount=0;//可以通过是否为0判断文件是否有分类
			headerCount=0;
			archiveCount=0;
			dirCount=0;
			MyBufferedReader myBufferedReader=new MyBufferedReader(file, charset) {
				
				@Override
				protected void dealLine(String filePath,String charset,int lineNumber, String lineValue) {
					try {
						boolean check=false;
						if(!check)check=checkTag(filePath,charset,lineNumber,lineValue);
						if(!check)check=checkCategory(filePath,charset,lineNumber,lineValue);
						if(!check)check=checkBookmark(filePath,charset,lineNumber,lineValue);
						if(!check)check=checkHeader(filePath,charset,lineNumber,lineValue);
						if(!check)check=checkArchive(filePath,charset,lineNumber,lineValue);
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
				
				/**
				 * 
				 * @param filePath
				 * @param charset
				 * @param lineNumber
				 * @param lineValue
				 * @return
				 */
				private boolean checkHeader(String filePath,String charset,int lineNumber, String lineValue) {
					//TODO 标题
					Matcher matcher=headerPattern.matcher(lineValue);
					boolean result=false;
					result=matcher.find();
					if(result){
						String header=matcher.group(2);
						if(Debug.debug)System.out.println("h:"+header);
					}
					return result;
				}
	
				private boolean checkBookmark(String filePath,String charset,int lineNumber, String lineValue) throws InstantiationException, IllegalAccessException {
					Matcher matcher=bookmarkPattern.matcher(lineValue);
					boolean result=false;
					result=matcher.find();
					if(result){
						String bookmark=matcher.group(2);
						if(Debug.debug)System.out.println("b:"+bookmark);
						String[] bookmarkHeads=bookmark.split("/");
						List<Object> merge=new ArrayList<Object>();
						if(bookmarkHeads.length>1) {
							for(int i=0;i<bookmarkHeads.length-1;i++) {
								merge.add(bookmarkHeads[i]);
							}
						}
						merge.add(0, "bookmark");
						TableHeaderTreeLeafWithoutId tableHeaderTreeLeafWithoutId=new TableHeaderTreeLeafWithoutId();
						TableHeader tableHeader=new TableHeader();
						tableHeader.setHeader(bookmarkHeads[bookmarkHeads.length-1].toString());
						tableHeader.setFilePath(filePath);
						tableHeader.setIndex(bookMarkCount++);
						tableHeader.setLastHeader(true);
						if(merge.size()>0)tableHeaderTreeLeafWithoutId.setMerge(merge);
						tableHeaderTreeLeafWithoutId.setAddAtLast(false);
						tableHeaderTreeLeafWithoutId.setTableHeader(tableHeader);
						bookmarks.addLeaf(tableHeaderTreeLeafWithoutId);
					}
					return result;
				}
	
				private boolean checkCategory(String filePath,String charset,int lineNumber, String lineValue) throws InstantiationException, IllegalAccessException {
					Matcher matcher=categoryPattern.matcher(lineValue);
					boolean result=false;
					result=matcher.find();
					if(result){
						String category=matcher.group(2);
						if(Debug.debug)System.out.println("c:"+category);
						String[] categoryHeaders=category.split("/");
						List<Object> merge=new ArrayList<Object>();
						if(categoryHeaders.length!=0) {
							for(int i=0;i<categoryHeaders.length;i++) {
								merge.add(categoryHeaders[i]);
							}
						}
						merge.add(0,"category");
						TableHeaderTreeLeafWithoutId tableHeaderTreeLeafWithoutId=new TableHeaderTreeLeafWithoutId();
						TableHeader tableHeader=new TableHeader();
						tableHeader.setHeader(categoryHeaders[categoryHeaders.length-1].toString());
						tableHeader.setFilePath(filePath);
						tableHeader.setIndex(categoryCount++);
						tableHeader.setLastHeader(true);
						if(merge.size()>0)tableHeaderTreeLeafWithoutId.setMerge(merge);
						tableHeaderTreeLeafWithoutId.setAddAtLast(false);
						tableHeaderTreeLeafWithoutId.setTableHeader(tableHeader);
						categories.addLeaf(tableHeaderTreeLeafWithoutId);
					}
					return result;
				}
				
				private boolean checkArchive(String filePath, String charset, int lineNumber, String lineValue) throws InstantiationException, IllegalAccessException{
					Matcher matcher=archivePattern.matcher(lineValue);
					boolean result=false;
					result=matcher.find();
					if(result){
						String archive=matcher.group(2);
						if(Debug.debug)System.out.println("a:"+archive);
						String[] archiveHeaders=archive.split("/");
						List<Object> merge=new ArrayList<Object>();
						if(archiveHeaders.length!=0) {
							for(int i=0;i<archiveHeaders.length;i++) {
								merge.add(archiveHeaders[i]);
							}
						}
						merge.add(0,"archive");
						TableHeaderTreeLeafWithoutId tableHeaderTreeLeafWithoutId=new TableHeaderTreeLeafWithoutId();
						TableHeader tableHeader=new TableHeader();
						tableHeader.setHeader(archiveHeaders[archiveHeaders.length-1].toString());
						tableHeader.setFilePath(filePath);
						tableHeader.setIndex(archiveCount++);
						tableHeader.setLastHeader(true);
						if(merge.size()>0)tableHeaderTreeLeafWithoutId.setMerge(merge);
						tableHeaderTreeLeafWithoutId.setAddAtLast(false);
						tableHeaderTreeLeafWithoutId.setTableHeader(tableHeader);
						archives.addLeaf(tableHeaderTreeLeafWithoutId);
					}
					return result;
				}
	
				private boolean checkTag(String filePath,String charset,int lineNumber, String lineValue) throws InstantiationException, IllegalAccessException {
					Matcher matcher=tagPattern.matcher(lineValue);
					boolean result=false;
					result=matcher.find();
					if(result){
						String tag=matcher.group(2);
						if(Debug.debug)System.out.println("t:"+tag);
						String[] tagHeaders=tag.split("/");
						List<Object> merge=new ArrayList<Object>();
						if(tagHeaders.length!=0) {
							for(int i=0;i<tagHeaders.length;i++) {
								merge.add(tagHeaders[i]);
							}
						}
						merge.add(0,"tag");
						TableHeaderTreeLeafWithoutId tableHeaderTreeLeafWithoutId=new TableHeaderTreeLeafWithoutId();
						TableHeader tableHeader=new TableHeader();
						tableHeader.setHeader(tagHeaders[tagHeaders.length-1].toString());
						tableHeader.setFilePath(filePath);
						tableHeader.setIndex(tagCount++);
						tableHeader.setLastHeader(true);
						if(merge.size()>0)tableHeaderTreeLeafWithoutId.setMerge(merge);
						tableHeaderTreeLeafWithoutId.setAddAtLast(false);
						tableHeaderTreeLeafWithoutId.setTableHeader(tableHeader);
						tags.addLeaf(tableHeaderTreeLeafWithoutId);
					}
					return result;
				}
			};
			myBufferedReader.read();
			myBufferedReader.close();
			//文件目录
			if(!file.isDirectory()) {
				String dir=file.getParentFile().getAbsolutePath().replace(mdUploadDir, "md").replaceAll("\\\\", "/");
				String[] dirHeaders=dir.split("/");
				List<Object> merge=new ArrayList<Object>();
				if(dirHeaders.length!=0) {
					for(int i=0;i<dirHeaders.length;i++) {
						merge.add(dirHeaders[i]);
					}
				}
				merge.add(0,"dir");
				TableHeaderTreeLeafWithoutId tableHeaderTreeLeafWithoutId=new TableHeaderTreeLeafWithoutId();
				TableHeader tableHeader=new TableHeader();
				tableHeader.setHeader(dirHeaders[dirHeaders.length-1].toString());
				tableHeader.setFilePath(file.getAbsolutePath());
				tableHeader.setIndex(dirCount++);
				tableHeader.setLastHeader(true);
				if(merge.size()>0)tableHeaderTreeLeafWithoutId.setMerge(merge);
				tableHeaderTreeLeafWithoutId.setAddAtLast(false);
				tableHeaderTreeLeafWithoutId.setTableHeader(tableHeader);
				dirs.addLeaf(tableHeaderTreeLeafWithoutId);
				if(Debug.debug)System.out.println(file.getName());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		PrintCurStackTrace.println();
		StringBuilder result=new StringBuilder();
		result.append("\r\n[TOCM]\r\n").append("\r\n[TOC]\r\n");
		
		//TODO 书签导航
		//书签
		List<MyTreeNode> bookmarkW=new LinkedList<MyTreeNode>();
		if(bookmarks.getRoot()!=null){
			bookmarks.sortByIsLastLeaf();
			bookmarks.clear();
			while(bookmarks.hasNext()) {
				MyTreeNode next=bookmarks.next();
				TableHeader tableHeader=(TableHeader) next.getBindObject();
				if(!next.isRoot()) {
					int parentCount=next.getParentCount();
					if(tableHeader.isLastHeader()) {
						result.append("\r\n![书签图标已失效!](sys_pictures/bookmark.jpg \"书签\")&nbsp;&nbsp;["
								+tableHeader.getHeader()
								+"](md/"
								+tableHeader.getFilePath().replace(mdUploadDir, "").replaceAll("\\\\", "/")
								+"?bk="+tableHeader.getIndex()+")\r\n");	
					} else {
						result.append("\r\n");
						for(int i=0;i<parentCount;i++) {
							result.append("#");
						}
						result.append(" "+tableHeader.getHeader()+"\r\n");
					}
				}
			}
		}
		
		int preParentCount=0;
		int nextParentCount=0;
		//分类
		if(categories.getRoot()!=null){
			categories.sortByIsLastLeaf();
			categories.clear();
			while(categories.hasNext()) {
				MyTreeNode next=categories.next();
				
//				nextParentCount=next.getParentCount();
//				if(nextParentCount!=preParentCount&&preParentCount<nextParentCount&&preParentCount!=0) {
//					result.append("\r\n");
//					for(int i=0;i<nextParentCount+1;i++) {
//						result.append("#");
//					}
//					TableHeader t=(TableHeader) next.getBindObject();
//					result.append(" "+t.getHeader()+"\r\n");
//				}
//				preParentCount=nextParentCount;
				
				TableHeader tableHeader=(TableHeader) next.getBindObject();
				if(!next.isRoot()) {
					int parentCount=next.getParentCount();
					if(tableHeader.isLastHeader()) {
						String filePath=tableHeader.getFilePath().replace(mdUploadDir, "").replaceAll("\\\\", "/");
						String[] splits=filePath.split("/");
						result.append("\r\n![分类图标已失效!](sys_pictures/category.jpg \"分类\")&nbsp;&nbsp;["+splits[splits.length-1]+"](md/"+filePath+")\r\n");	
					} else {
						result.append("\r\n");
						for(int i=0;i<parentCount;i++) {
							result.append("#");
						}
						result.append(" "+tableHeader.getHeader()+"\r\n");
					}
				}
			}
		}
		
		//标签
		if(tags.getRoot()!=null){
			tags.sortByIsLastLeaf();
			tags.clear();
			while(tags.hasNext()) {
				MyTreeNode next=tags.next();
				
//				nextParentCount=next.getParentCount();
//				if(nextParentCount!=preParentCount&&preParentCount<nextParentCount&&preParentCount!=0) {
//					result.append("\r\n");
//					for(int i=0;i<nextParentCount+1;i++) {
//						result.append("#");
//					}
//					TableHeader t=(TableHeader) next.getBindObject();
//					result.append(" "+t.getHeader()+"\r\n");
//				}
//				preParentCount=nextParentCount;
				
				TableHeader tableHeader=(TableHeader) next.getBindObject();
				if(!next.isRoot()) {
					int parentCount=next.getParentCount();
					if(tableHeader.isLastHeader()) {
						String filePath=tableHeader.getFilePath().replace(mdUploadDir, "").replaceAll("\\\\", "/");
						String[] splits=filePath.split("/");
						result.append("\r\n![标签图标已失效!](sys_pictures/tag.jpg \"标签\")&nbsp;&nbsp;["+splits[splits.length-1]+"](md/"+filePath+")\r\n");	
					} else {
						result.append("\r\n");
						for(int i=0;i<parentCount;i++) {
							result.append("#");
						}
						result.append(" "+tableHeader.getHeader()+"\r\n");
					}
				}
			}
		}
		
		//归档
		if(archives.getRoot()!=null){
			archives.sortByIsLastLeaf();
			archives.clear();
			while(archives.hasNext()) {
				MyTreeNode next=archives.next();
				
//				nextParentCount=next.getParentCount();
//				if(nextParentCount!=preParentCount&&preParentCount<nextParentCount&&preParentCount!=0) {
//					result.append("\r\n");
//					for(int i=0;i<nextParentCount+1;i++) {
//						result.append("#");
//					}
//					TableHeader t=(TableHeader) next.getBindObject();
//					result.append(" "+t.getHeader()+"\r\n");
//				}
//				preParentCount=nextParentCount;
				
				TableHeader tableHeader=(TableHeader) next.getBindObject();
				if(!next.isRoot()) {
					int parentCount=next.getParentCount();
					if(tableHeader.isLastHeader()) {
						String filePath=tableHeader.getFilePath().replace(mdUploadDir, "").replaceAll("\\\\", "/");
						String[] splits=filePath.split("/");
						result.append("\r\n![归档图标已失效!](sys_pictures/archive.jpg \"归档\")&nbsp;&nbsp;["+splits[splits.length-1]+"](md/"+filePath+")\r\n");	
					} else {
						result.append("\r\n");
						for(int i=0;i<parentCount;i++) {
							result.append("#");
						}
						result.append(" "+tableHeader.getHeader()+"\r\n");
					}
				}
			}
		}
		
		//目录
		if(dirs.getRoot()!=null){
			dirs.sortByIsLastLeaf();
			dirs.clear();
			while(dirs.hasNext()) {
				MyTreeNode next=dirs.next();
				
//				nextParentCount=next.getParentCount();
//				if(nextParentCount!=preParentCount&&preParentCount<nextParentCount) {
//					result.append("\r\n");
//					for(int i=0;i<nextParentCount+1;i++) {
//						result.append("#");
//					}
//					TableHeader t=(TableHeader) next.getBindObject();
//					result.append(" "+t.getHeader()+"\r\n");
//				}
//				preParentCount=nextParentCount;
				
				TableHeader tableHeader=(TableHeader) next.getBindObject();
				if(!next.isRoot()) {
					int parentCount=next.getParentCount();
					if(tableHeader.isLastHeader()) {
						String filePath=tableHeader.getFilePath().replace(mdUploadDir, "").replaceAll("\\\\", "/");
						String[] splits=filePath.split("/");
						result.append("\r\n![目录图标已失效!](sys_pictures/dir.jpg \"目录\")&nbsp;&nbsp;["+splits[splits.length-1]+"](md/"+filePath+")\r\n");	
					} else {
						result.append("\r\n");
						for(int i=0;i<parentCount;i++) {
							result.append("#");
						}
						result.append(" "+tableHeader.getHeader()+"\r\n");
					}
				}
			}
		}
		
		//标题
		result.append("\r\n# header\r\n");
		
		//过滤标签
		result.append("\r\n# bookmark filter\r\n");
		
		return result.toString();
	}
	
}

	
