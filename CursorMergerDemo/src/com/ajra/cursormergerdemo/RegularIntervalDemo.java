package com.ajra.cursormergerdemo;

import java.util.HashMap;

import com.ajra.cursormarger.CursorCreator;
import com.ajra.cursormerger.database.BookContentProvider;

import android.database.Cursor;
import android.graphics.Color;

public class RegularIntervalDemo extends BaseActivity {

	@Override
	protected void performCursorManipulation(Cursor actualCursor) {
		// cursor row to be inserted
		HashMap<String, Object> cursorData1 = new HashMap<String, Object>();
		cursorData1.put("_id", -1);
		cursorData1.put(BookContentProvider.KEY_ROW_IDENTIFIER,
				ListAdapter.USER_INSERTED_ROW_IDENTIFIER);
		cursorData1.put(KEY_USER_INSERTED_TITLE, "Android");
		cursorData1.put(KEY_COLOR, Color.YELLOW);
		Cursor cursorRow1 = CursorCreator.getCursorRow(cursorData1);

		Cursor mergedCursor = CursorCreator.mergeCursorAtInterval(cursorRow1,
				actualCursor, 3);

		adapter.changeCursor(mergedCursor);

	}

}
