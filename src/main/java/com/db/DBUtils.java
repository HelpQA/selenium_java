package com.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



public class DBUtils {

	/*public static Object[][] returnData(String query) {
		Collection<User> user = QueryHelper.getObject(query);

		List<List<Object>> list = new ArrayList<>();
		for (User u : user) {
			List<Object> row = new ArrayList<>();
			row.add(u);
			list.add(row);
		}
		return convertData(list);
	}*/

	private static Object[][] convertData(List<List<Object>> fileData) {
		Object[][] extractedData = null;
		if (fileData != null && fileData.size() > 0) {
			extractedData = new Object[fileData.size()][fileData.get(0).size()];
			int row = 0;
			for (List<Object> rowData : fileData) {
				int column = 0;
				for (Object cellData : rowData) {
					extractedData[row][column] = cellData;
					column++;
				}
				row++;
			}
		}
		return extractedData;
	}
}
