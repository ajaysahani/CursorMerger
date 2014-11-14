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

import android.annotation.TargetApi;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.os.Build;

/**
 * 
 * @author Ajay Sahani
 * @see: Class responsible for merging cursors inside cursor.
 * 
 */
public class CursorMerger extends AbstractCursor {

	public static String TAG=CursorMerger.class.getSimpleName();
	private Cursor currentRowCursor;
	private Cursor actualCursor;
	/**
	 * Bucket which contain all row which we would like to be inserted in actual
	 * row. HashMap<Integer, Cursor>: key:position of cursor where it is
	 * inserted and value:cursor to be inserted
	 */
	private HashMap<Integer, Cursor> cursorBucket;

	protected CursorMerger(Cursor actualCursor,
			HashMap<Integer, Cursor> cursorBucket) {
		this.actualCursor = actualCursor;
		if (actualCursor != null) {
			currentRowCursor = actualCursor;
		} else if (cursorBucket != null && cursorBucket.size() > 0) {
			// If actual cursor is null ,than will consider our current cursor
			// is first cursor from cursor bucket.
			currentRowCursor = cursorBucket.get(0);
		} else {
			new IllegalArgumentException(
					"Actual cursor and cursor bucket both can not be null or size can not less than 1");
		}
		this.cursorBucket = cursorBucket;
	}

	@Override
	public int getCount() {
		// calculate cursor size by including actual cursor and cursor bucket.
		int count = 0;
		if (actualCursor != null) {
			count = actualCursor.getCount();
		}
		if (cursorBucket != null) {
			count = count + cursorBucket.size();
		}
		return count;
	}

	@Override
	public boolean onMove(int oldPosition, int newPosition) {
		/* Find the right cursor */
		currentRowCursor = null;
		boolean flag = false;
		if (cursorBucket != null && cursorBucket.containsKey(newPosition)) {
			currentRowCursor = cursorBucket.get(newPosition);
			flag = currentRowCursor.moveToFirst();
		} else if (actualCursor != null) {
			currentRowCursor = actualCursor;
			int actualPosition = getActualPosition(newPosition);
			flag = currentRowCursor.moveToPosition(actualPosition);
		}
		return flag;
	}

	/**
	 * Check no of position occupied by suggested content example:new position
	 * is 4 but position 3 already occupied by row which we inserted ,than
	 * calculate actual position
	 * 
	 * @param newPosition
	 * @return actual position of cursor
	 */
	private int getActualPosition(int newPosition) {
		int actualPosition = 0;
		int noOfPositionOccupied = 0;
		for (int count = newPosition - 1; count >= 0; count--) {
			if (null != cursorBucket && cursorBucket.containsKey(count)) {
				noOfPositionOccupied++;
			}
		}
		actualPosition = newPosition - noOfPositionOccupied;
		return actualPosition;
	}

	@Override
	public String getString(int column) {
		return currentRowCursor.getString(column);
	}

	@Override
	public short getShort(int column) {
		return currentRowCursor.getShort(column);
	}

	@Override
	public int getInt(int column) {
		return currentRowCursor.getInt(column);
	}

	@Override
	public long getLong(int column) {
		return currentRowCursor.getLong(column);
	}

	@Override
	public float getFloat(int column) {
		return currentRowCursor.getFloat(column);
	}

	@Override
	public double getDouble(int column) {
		return currentRowCursor.getDouble(column);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public int getType(int column) {
		return currentRowCursor.getType(column);
	}

	@Override
	public boolean isNull(int column) {
		return currentRowCursor.isNull(column);
	}

	@Override
	public byte[] getBlob(int column) {
		return currentRowCursor.getBlob(column);
	}

	@Override
	public String[] getColumnNames() {
		if (currentRowCursor != null) {
			return currentRowCursor.getColumnNames();
		} else {
			return new String[0];
		}
	}

	@Override
	public void close() {
		if (actualCursor != null) {
			actualCursor.close();
		}
		super.close();
	}

}
