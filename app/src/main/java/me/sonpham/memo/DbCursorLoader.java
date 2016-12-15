package me.sonpham.memo;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;

import me.sonpham.memo.data.DbOpenHelper;
import me.sonpham.memo.data.DbUtils;

/**
 * Created by sp on 3/19/15.
 */


// http://stackoverflow.com/questions/18326954/how-to-read-an-sqlite-db-in-android-with-a-cursorloader
public class DbCursorLoader extends CursorLoader{
	public DbCursorLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		super(context, uri, projection, selection, selectionArgs, sortOrder);
	}

	@Override
	public Cursor loadInBackground() {
		return DbUtils.queryMemo(getContext(), getProjection(), getSelection(), getSelectionArgs(), null, null, getSortOrder());
	}
}
