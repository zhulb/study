package com.zlb.markdown.moudle.fileSystem;

import com.alibaba.fastjson.JSONObject;
import com.zlb.markdown.moudle.GetIndexTree;
import com.zlb.markdown.moudle.fileSystem.tool.FenLei;
import com.zlb.markdown.moudle.fileSystem.tool.GetDir;
import com.zlb.markdown.moudle.fileSystem.tool.PrintCurStackTrace;
import com.zlb.markdown.moudle.fileSystem.tool.Source;

public class GetFileSystemDirectoryIndexTree implements GetIndexTree {
	
	private String target="";

	/**
	 * 获取文件系统的目录树
	 */
	public JSONObject getIndexTree() {
		
		PrintCurStackTrace.println();
		GetDir getDir=GetDir.getInstance();
		return getDir.getDirLoop(target,new String[]{".md",".txt"});
	}
	
	public void setTarget(String target){
		this.target=target;
	}


}
