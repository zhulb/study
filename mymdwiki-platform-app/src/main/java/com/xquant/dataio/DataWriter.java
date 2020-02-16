package com.xquant.dataio;

import java.util.Map;

public interface DataWriter<T extends MetaData> {

	void writeMetaData(T readMetaData);

	void writeRow(Map<String, Object> row);

}
