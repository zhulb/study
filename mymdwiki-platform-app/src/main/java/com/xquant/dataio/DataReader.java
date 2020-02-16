package com.xquant.dataio;

import java.util.Map;

import com.xquant.dataio.impl.table.TableDataReaderConfig;

public interface DataReader<C extends DataReaderConfig,T extends MetaData> {

	T readMetaData();
	
	boolean hasNextRow();
	
	Map<String,Object> nextRow();

	C generateReaderConfig();

}
