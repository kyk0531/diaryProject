package me.sonpham.memo.data;

/**
 * Created by sp on 3/19/15.
 */
public class MemoContract {
	public static final class MemoEntry {
		public static final String TABLE_NAME = "memo";

		// fields
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_ID_TYPE = "INTEGER PRIMARY KEY NOT NULL";

		public static final String COLUMN_HEADER = "header";
		public static final String COLUMN_HEADER_TYPE = "TEXT NOT NULL";

		public static final String COLUMN_DATE = "date";
		public static final String COLUMN_DATE_TYPE = "TEXT";

		public static final String COLUMN_TIME = "time";
		public static final String COLUMN_TIME_TYPE = "TEXT";

		public static final String COLUMN_LOCATION = "location";
		public static final String COLUMN_LOCATION_TYPE = "TEXT";

		public static final String COLUMN_BODY = "body";
		public static final String COLUMN_BODY_TYPE = "TEXT";

		public static final String COLUMN_TIMESTAMP = "timestamp";
		public static final String COLUMN_TIMESTAMP_TYPE = "INTEGER NOT NULL";

		// sql statements
		public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
				+ COLUMN_ID + " " + COLUMN_ID_TYPE + ", "
				+ COLUMN_HEADER + " " + COLUMN_HEADER_TYPE + ", "
				+ COLUMN_BODY + " " + COLUMN_BODY_TYPE + ", "
				+ COLUMN_DATE + " " + COLUMN_DATE_TYPE + ", "
				+ COLUMN_TIME + " " + COLUMN_TIME_TYPE + ", "
				+ COLUMN_LOCATION + " " + COLUMN_LOCATION_TYPE
				+ ");";

		public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

		public static final String TABLE_DELETE = "DELETE FROM " + TABLE_NAME + ";";
	}
}
