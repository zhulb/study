package com.xquant.dataio.impl.table;

import java.util.Map;

import com.xquant.dataio.DataReader;

public class TableDataReader implements DataReader<TableDataReaderConfig,TableMetaData>{


	@Override
	public Map<String, Object> nextRow() {
		return null;
	}

	@Override
	public TableDataReaderConfig generateReaderConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TableMetaData readMetaData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasNextRow() {
		// TODO Auto-generated method stub
		return false;
	}

}
