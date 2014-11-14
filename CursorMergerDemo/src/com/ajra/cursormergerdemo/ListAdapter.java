package com.ajra.cursormergerdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ajra.cursormargerdemo.R;
import com.ajra.cursormerger.database.BookContentProvider;

public class ListAdapter extends CursorAdapter {

	private LayoutInflater inflater;
	public static final int FROM_DATABASE_IDENTIFIER = 1;
	public static final int USER_INSERTED_ROW_IDENTIFIER = 2;
	private PositionChangeListener positionChangeListener;

	public ListAdapter(Activity activity, Cursor cursor) {
		super(activity.getApplicationContext(), cursor, false);
		this.inflater = LayoutInflater.from(activity);
	}

	public void setPositionChangeListener(
			PositionChangeListener positionChangeListener) {
		this.positionChangeListener = positionChangeListener;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		Holder holder = (Holder) view.getTag();
		setValue(holder, cursor);
		if (positionChangeListener != null) {
			positionChangeListener.onPositionChange(cursor.getPosition());
		}
	}

	@SuppressLint("InflateParams")
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		View convertView = inflater.inflate(R.layout.list_row, null);
		Holder holder = new Holder();
		holder.nameTxtView = (TextView) convertView.findViewById(R.id.name);
		holder.fromTxtView = (TextView) convertView.findViewById(R.id.from);
		holder.parentView = convertView;
		convertView.setTag(holder);
		setValue(holder, cursor);
		return convertView;
	}

	private void setValue(Holder holder, Cursor cursor) {

		final int rowIdentifier = cursor.getInt(cursor
				.getColumnIndex(BookContentProvider.KEY_ROW_IDENTIFIER));
		String title = "";
		if (rowIdentifier == FROM_DATABASE_IDENTIFIER) {
			title = cursor.getString(cursor
					.getColumnIndex(BookContentProvider.KEY_BOOK_NAME));
			holder.fromTxtView.setText("");
			holder.parentView.setBackgroundColor(Color.WHITE);
			holder.nameTxtView.setText(title);
		} else {
			int color = cursor.getInt(cursor
					.getColumnIndex(BaseActivity.KEY_COLOR));
			title = cursor.getString(cursor
					.getColumnIndex(BaseActivity.KEY_USER_INSERTED_TITLE));
			holder.fromTxtView.setText("Manually Added Row");
			holder.nameTxtView.setText(title);
			holder.parentView.setBackgroundColor(color);
		}
	}

	class Holder {
		View parentView;
		TextView nameTxtView;
		TextView fromTxtView;

	}

	public static interface PositionChangeListener {
		public void onPositionChange(int position);
	}
}