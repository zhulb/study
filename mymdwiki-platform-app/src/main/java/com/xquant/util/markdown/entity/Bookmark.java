package com.xquant.util.markdown.entity;

import java.util.List;

public class Bookmark {
	private String filepath;
	private int line;
	private String bookmark;
	private List<String> merges;
	public List<String> getMerges() {
		return merges;
	}
	public void setMerges(List<String> merges) {
		this.merges = merges;
	}
	public String getBookmark() {
		return bookmark;
	}
	public void setBookmark(String bookmark) {
		this.bookmark = bookmark;
	}
	private int index;
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
}
