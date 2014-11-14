package com.ajra.cursormergerdemo;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.ajra.cursormargerdemo.R;
import com.ajra.cursormerger.database.BookContentProvider;

@SuppressLint({ "UseSparseArrays", "InflateParams" })
public abstract class BaseActivity extends FragmentActivity implements
		LoaderManager.LoaderCallbacks<Cursor> {

	private ListView listView;
	protected ListAdapter adapter;
	private int bookCount;

	// column name for dummy row
	public static final String KEY_COLOR = "color";
	public static final String KEY_USER_INSERTED_TITLE = "title";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_page_main);
		bookCount = getCursorCount();
		if (getCursorCount() < 1) {
			insertBookIntoDatabase(10);
		}
		getSupportLoaderManager().initLoader(0, null, this);
		setListViewData();
	}

	private void setListViewData() {
		listView = (ListView) findViewById(R.id.listView);
		adapter = new ListAdapter(this, null);
		listView.setAdapter(adapter);
		setPositionChangeListener();
		View loadMoreView = getLayoutInflater().inflate(
				R.layout.load_more_view, null);
		listView.addFooterView(loadMoreView);
	}

	private void setPositionChangeListener() {
		adapter.setPositionChangeListener(new ListAdapter.PositionChangeListener() {

			@Override
			public void onPositionChange(int position) {
				if (adapter != null && position == (adapter.getCount() - 1)) {
					Runnable task = new Runnable() {

						@Override
						public void run() {
							insertBookIntoDatabase(5);
						}
					};
					Executors.newSingleThreadScheduledExecutor().schedule(task,
							3, TimeUnit.SECONDS);
				}

			}
		});
	}

	private void insertBookIntoDatabase(int limitCount) {
		for (int count = 0; count < limitCount; count++) {
			ContentValues values = new ContentValues();
			values.put(BookContentProvider.KEY_BOOK_NAME, "Book "
					+ (++bookCount));
			values.put(BookContentProvider.KEY_ROW_IDENTIFIER,
					ListAdapter.FROM_DATABASE_IDENTIFIER);
			getContentResolver().insert(BookContentProvider.BOOK_TABLE_URI,
					values);
		}
	}

	private int getCursorCount() {
		int count = 0;
		Cursor cursor = getContentResolver().query(
				BookContentProvider.BOOK_TABLE_URI, null, null, null, null);
		if (cursor != null) {
			count = cursor.getCount();
			cursor.close();
		}
		return count;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle bundle) {
		return new CursorLoader(getApplicationContext(),
				BookContentProvider.BOOK_TABLE_URI, null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		performCursorManipulation(cursor);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.changeCursor(null);
	}

	protected abstract void performCursorManipulation(Cursor actualCursor);
}
