package com.xquant.util.markdown;

import java.io.File;

public interface EventConfig {

	String getOutDir();
	
	String createOutDir();

	void afterReaderAll();

	void onGetNextFile(File file,String charset);

}
