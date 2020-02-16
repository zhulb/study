package com.xquant.util.markdown.entity;

/**
 * 标签云
 * @author zhulb
 *
 */
public class Tag {

	private String filePath;
	private String tag;
	private String parentTag;
	public String getParentTag() {
		return parentTag;
	}
	public void setParentTag(String parentTag) {
		this.parentTag = parentTag;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
