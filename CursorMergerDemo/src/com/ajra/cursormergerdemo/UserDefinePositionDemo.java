package com.ajra.cursormergerdemo;

import java.util.HashMap;

import com.ajra.cursormarger.CursorCreator;
import com.ajra.cursormerger.database.BookContentProvider;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;

public class UserDefinePositionDemo extends BaseActivity {

	@SuppressLint("UseSparseArrays")
	@Override
	protected void performCursorManipulation(Cursor actualCursor) {
		HashMap<Integer, Cursor> cursorBucket = new HashMap<Integer, Cursor>();

		// First Cursor
		HashMap<String, Object> cursorData1 = new HashMap<String, Object>();
		cursorData1.put("_id", -1);
		cursorData1.put(BookContentProvider.KEY_ROW_IDENTIFIER,
				ListAdapter.USER_INSERTED_ROW_IDENTIFIER);
		cursorData1.put(KEY_USER_INSERTED_TITLE, "Android");
		cursorData1.put(KEY_COLOR, Color.YELLOW);
		Cursor cursorRow1 = CursorCreator.getCursorRow(cursorData1);

		// second cursor
		HashMap<String, Object> cursorData2 = new HashMap<String, Object>();
		cursorData2.put("_id", -2);
		cursorData2.put(BookContentProvider.KEY_ROW_IDENTIFIER,
				ListAdapter.USER_INSERTED_ROW_IDENTIFIER);
		cursorData2.put(KEY_USER_INSERTED_TITLE, "Iphone");
		cursorData2.put(KEY_COLOR, Color.GREEN);
		Cursor cursorRow2 = CursorCreator.getCursorRow(cursorData2);

		// third cursor
		HashMap<String, Object> cursorData3 = new HashMap<String, Object>();
		cursorData3.put("_id", -3);
		cursorData3.put(BookContentProvider.KEY_ROW_IDENTIFIER,
				ListAdapter.USER_INSERTED_ROW_IDENTIFIER);
		cursorData3.put(KEY_USER_INSERTED_TITLE, "Windows Phone");
		cursorData3.put(KEY_COLOR, Color.RED);
		Cursor cursorRow3 = CursorCreator.getCursorRow(cursorData3);

		// fourth cursor
		HashMap<String, Object> cursorData4 = new HashMap<String, Object>();
		cursorData4.put("_id", -4);
		cursorData4.put(BookContentProvider.KEY_ROW_IDENTIFIER,
				ListAdapter.USER_INSERTED_ROW_IDENTIFIER);
		cursorData4.put(KEY_USER_INSERTED_TITLE, "Blackberry");
		cursorData4.put(KEY_COLOR, Color.MAGENTA);
		Cursor cursorRow4 = CursorCreator.getCursorRow(cursorData4);

		cursorBucket.put(2, cursorRow1);
		cursorBucket.put(4, cursorRow2);
		cursorBucket.put(5, cursorRow3);
		cursorBucket.put(8, cursorRow4);

		Cursor mergedCursor = CursorCreator.mergeCursor(actualCursor,
				cursorBucket);

		adapter.changeCursor(mergedCursor);

	}

}
