package com.xquant.dataio.impl.table;

import java.util.Map;

import com.xquant.dataio.DataReader;
import com.xquant.dataio.DataReaderConfig;
import com.xquant.dataio.DataWriter;
import com.xquant.dataio.MetaData;

public class Test {
	public static void main(String[] args) {
		DataReader<TableDataReaderConfig, TableMetaData> dataReader = new TableDataReader();
		DataWriter<TableMetaData> dataWriter = new TextDataWriter();
		
		TableDataReaderConfig generateReaderConfig = dataReader.generateReaderConfig();
		
		
		TableMetaData readMetaData = dataReader.readMetaData();
		dataWriter.writeMetaData(readMetaData);
		
		Map<String,Object> row = null;
		while(dataReader.hasNextRow()) {
			row = dataReader.nextRow();
			dataWriter.writeRow(row);
		}
		
	}
}
