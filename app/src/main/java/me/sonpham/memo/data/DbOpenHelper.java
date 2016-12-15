package me.sonpham.memo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sp on 3/19/15.
 */
public class DbOpenHelper extends SQLiteOpenHelper{
	private static final int DATABASE_VERSION = 1;
	static final String DATABASE_NAME = "memo" + ".db";

	public DbOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(MemoContract.MemoEntry.TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(MemoContract.MemoEntry.TABLE_DROP);
		onCreate(db);
	}
}
