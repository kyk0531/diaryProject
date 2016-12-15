package me.sonpham.memo;

import android.content.Context;
import android.database.Cursor;
import android.os.DropBoxManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import me.sonpham.memo.data.MemoContract;

/**
 * Created by sp on 3/20/15.
 */
public class ListAdapter extends CursorAdapter {
	public ListAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return LayoutInflater.from(context).inflate(R.layout.fragment_list_item, parent, false);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView header = (TextView) view.findViewById(R.id.fragment_list_item_header);
		TextView body = (TextView) view.findViewById(R.id.fragment_list_item_body);
		header.setText(cursor.getString(cursor.getColumnIndex(MemoContract.MemoEntry.COLUMN_HEADER)));
		body.setText(cursor.getString(cursor.getColumnIndex(MemoContract.MemoEntry.COLUMN_BODY)));
	}
}
