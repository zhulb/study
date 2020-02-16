package com.xquant.util.markdown.entity;

/**
 * 分类
 * @author zhulb
 *
 */
public class Category {
	private String filePath;
	private String category;
	private String parentCategory;
	public String getParentCategory() {
		return parentCategory;
	}
	public void setParentCategory(String parentCategory) {
		this.parentCategory = parentCategory;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

}
