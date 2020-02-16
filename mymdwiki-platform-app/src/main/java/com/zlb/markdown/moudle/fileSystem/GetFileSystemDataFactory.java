package com.zlb.markdown.moudle.fileSystem;

import java.io.File;

import com.zlb.markdown.moudle.GetDataFactory;
import com.zlb.markdown.moudle.GetFenLeiIndexTree;
import com.zlb.markdown.moudle.GetFileSource;
import com.zlb.markdown.moudle.GetIndexTree;
import com.zlb.markdown.moudle.GetTagIndexTree;
import com.zlb.markdown.moudle.fileSystem.tool.PathTool;

public class GetFileSystemDataFactory extends GetDataFactory {
	private String target=null;
	private String getTarget(){
		if(null == target){
			File classesFile=new File(PathTool.getClassesDirestory(this));
			this.target=classesFile.getParentFile().getParentFile().getPath().substring(6)+"/md";// windows 下 D:\dfad\adsfasf\fdsaf/md
		}
		
		return this.target;
	}
	//手动设置md文件夹的位置
	public void updateTarget(String target){
		this.target=target;
	}
	@Override
	public GetFileSource createFileSource() {
		//web系统中，文件系统可以通过ajax访问文件url直接拿取
		//不需要通过servlet拿文件资源
		return null;
	}

	@Override
	public GetIndexTree createGetDirectoryIndexTree() {
		GetFileSystemDirectoryIndexTree getIndexTree=new GetFileSystemDirectoryIndexTree();
		
		getIndexTree.setTarget(getTarget());
		
		
		return getIndexTree;
	}

	@Override
	public GetIndexTree createGetFenLeiIndexTree() {
		GetFileSystemFenLeiIndexTree getFileSystemFenLeiIndexTree=new GetFileSystemFenLeiIndexTree();
		getFileSystemFenLeiIndexTree.setTarget(getTarget());
		return getFileSystemFenLeiIndexTree;
	}

	@Override
	public GetIndexTree createGetTagIndexTree() {
		GetFileSystemTagIndexTree getFileSystemTagIndexTree=new GetFileSystemTagIndexTree();
		getFileSystemTagIndexTree.setTarget(getTarget());
		return getFileSystemTagIndexTree;
	}

}
