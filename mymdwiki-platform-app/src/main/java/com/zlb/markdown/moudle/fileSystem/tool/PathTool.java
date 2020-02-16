package com.zlb.markdown.moudle.fileSystem.tool;

/**
 * 路径工具
 * @author zhulb
 *
 */
public class PathTool {
	/**
	 * 获取class的包路径
	 * file:/D:/app/self/mymdwiki/WebRoot/WEB-INF/classes/com/zlb/markdown/moudle/fileSystem/
	 * @param targetClass
	 * @return
	 */
	public static String getClassPath(Object targetClass){
		return targetClass.getClass().getResource("").toString();

	}
	/**
	 * 获取classes文件夹，即class的最上层目录
	 * file:/D:/app/self/mymdwiki/WebRoot/WEB-INF/classes/
	 * @param targetClass
	 * @return
	 */
	public static String getClassesDirestory(Object targetClass){
		return targetClass.getClass().getClassLoader().getResource("").toString();
	}
}
