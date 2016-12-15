package me.sonpham.memo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by sp on 3/19/15.
 */
public final class DbUtils {
	public static final String LOG_TAG = DbUtils.class.getSimpleName();

	public static Cursor queryMemo(Context context, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
		return new DbOpenHelper(context)
				.getReadableDatabase()
				.query(MemoContract.MemoEntry.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
	}

	public static Cursor queryAllMemo(Context context) {
		return new DbOpenHelper(context)
				.getReadableDatabase()
				.query(MemoContract.MemoEntry.TABLE_NAME,null, null, null, null, null, MemoContract.MemoEntry.COLUMN_TIMESTAMP + " DESC", null);
	}

	public static long insertMemo(Context context, ContentValues values) throws SQLException{
		long _id = new DbOpenHelper(context)
				.getWritableDatabase()
				.insert(MemoContract.MemoEntry.TABLE_NAME, null, values);
		if (_id == -1) {
			throw new SQLException("Failed to insert values: " + values.toString());
		}
		return _id;
	}

	public static long insertMemoBulk(Context context, ContentValues[] values) throws SQLException{
		SQLiteDatabase db = new DbOpenHelper(context).getWritableDatabase();
		long count = 0;
		db.beginTransaction();
		try {
			for (ContentValues value : values) {
				long _id = db.insert(MemoContract.MemoEntry.TABLE_NAME, null, value);
				if (_id == -1) {
					throw new SQLException("Failed to insert values: " + value.toString());
				} else {
					count += 1;
				}
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		return count;
	}

	public static long update(Context context, ContentValues values, String where, String[] whereArgs) throws SQLException {
		long _id = new DbOpenHelper(context).getWritableDatabase()
				.update(MemoContract.MemoEntry.TABLE_NAME, values, where, whereArgs);
		if (_id == -1) {
			throw new SQLException("Failed to update values: " + values);
		} else {
			return _id;
		}
	}
}
