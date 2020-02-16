package com.xquant.util.markdown;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xquant.util.bufferedreader.MyBufferedReader;
import com.xquant.util.fileiterator.LimitedFileIterator;
import com.xquant.util.markdown.indexconfig.CreateIndexMdConfig;

public class Markdown {
	
	private List<EventConfig> eventConfigs=new LinkedList<EventConfig>();
	
	private List<String> dirs=new ArrayList<String>();
	
	private String charset = "UTF-8";
	
	public void add(String dir){
		dirs.add(dir);
	}
	
	public void addEventConfig(EventConfig eventConfig){
		eventConfigs.add(eventConfig);
	}
	
	public void loadAll() throws IOException{
		LimitedFileIterator fileIterator=new LimitedFileIterator();
		for(String dir:dirs){
			fileIterator.add(dir);
		}
		while(fileIterator.hasNext()){
			String nextFile=fileIterator.next();
			if(check(nextFile)){
				File file=new File(nextFile);
				if(file.isDirectory())continue;
				for(EventConfig eventConfig:this.eventConfigs) {
					eventConfig.onGetNextFile(file,this.charset);
				}
			}
		}
		
		for(int i=0;i<eventConfigs.size();i++){
			EventConfig executeConfig=eventConfigs.get(i);
			executeConfig.afterReaderAll();
		}
	}

	private boolean check(String nextFile) {
		return nextFile.endsWith(".md")||nextFile.endsWith(".markdown")||nextFile.endsWith(".txt")||nextFile.endsWith(".info");
	}
	
}

