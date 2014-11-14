/**
 * Copyright 2014-present Ajay Sahani
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.ajra.cursormarger;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.util.Log;

/**
 * 
 * @author Ajay Sahani
 * @see: Class responsible for creating cursor programatically.
 * 
 */
@SuppressLint("UseSparseArrays")
public class CursorCreator {

	private static boolean isDebugModeOn = false;

	/**
	 * A helper method which is responsible for creating cursor row by passing
	 * map which hold key as column name and value as column value.
	 * 
	 * @param cursorData
	 *            :a map which hold key as column name and value as column value
	 * @return :cursor
	 */
	public static synchronized Cursor getCursorRow(
			HashMap<String, Object> cursorData) {
		String[] columnName = new String[cursorData.size()];
		Object[] columnValue = new Object[cursorData.size()];
		int index = 0;
		for (Map.Entry<String, Object> entry : cursorData.entrySet()) {
			columnName[index] = entry.getKey();
			columnValue[index] = entry.getValue();
			index++;
		}
		MatrixCursor cursor = new MatrixCursor(columnName);
		cursor.addRow(columnValue);
		return cursor;
	}

	/**
	 * 
	 * @param actualCursor
	 * @param cursorBucket
	 *            :A hashmap which contain all cursor row which to be inserted.
	 * @return Cursor which have all dummy cursor row at developer define
	 *         position.
	 */
	public static synchronized Cursor mergeCursor(Cursor actualCursor,
			HashMap<Integer, Cursor> cursorBucket) {
		return new CursorMerger(actualCursor, cursorBucket);
	}

	/**
	 * This method used to insert same cursor row at given interval.
	 * 
	 * @param cursorRowToBeAdded
	 * @param actualCursor
	 * @param interval
	 *            :after which we have dummy row.
	 * @return Cursor which have all dummy cursor row at developer define
	 *         position.
	 */
	public static synchronized Cursor mergeCursorAtInterval(
			Cursor cursorRowToBeAdded, Cursor actualCursor, int interval) {
		int count = 0;
		int actualCursorCount = actualCursor.getCount();
		if (interval == 0) {
			interval = 1;
		}
		int noOfExtraItemAdded = (int) Math.ceil(actualCursorCount / interval);
		if (isDebugModeOn()) {
			Log.d(CursorMerger.TAG, "noOfExtraItemAdded:" + noOfExtraItemAdded
					+ " , actualCursorCount:" + actualCursorCount);
		}
		int totalCount = actualCursorCount + noOfExtraItemAdded;
		HashMap<Integer, Cursor> cursorBucket = new HashMap<Integer, Cursor>();
		for (int index = 0; index < totalCount; index++) {
			count = count + 1;
			if (count == (interval + 1)) {
				cursorBucket.put(index, cursorRowToBeAdded);
				count = 0;
			}
		}
		return mergeCursor(actualCursor, cursorBucket);
	}

	/**
	 * This method used to insert same cursor row at given interval.
	 * 
	 * @param cursorData
	 *            :data which is use to create cursor row ,which will be
	 *            inserted in actual cursor.
	 * @param actualCursor
	 * @param interval
	 * @return
	 */
	public static synchronized Cursor mergeCursorAtInterval(
			HashMap<String, Object> cursorData, Cursor actualCursor,
			int interval) {
		Cursor cursorRowToBeAdded = getCursorRow(cursorData);

		return mergeCursorAtInterval(cursorRowToBeAdded, actualCursor, interval);
	}


	public static boolean isDebugModeOn() {
		return isDebugModeOn;
	}

	public static void setDebugModeOn(boolean upDatedDebugMode) {
		isDebugModeOn = upDatedDebugMode;
	}
}
