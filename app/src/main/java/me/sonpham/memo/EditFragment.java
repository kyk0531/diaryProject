package me.sonpham.memo;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.gc.materialdesign.views.ProgressBarIndeterminate;

import java.sql.SQLException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.sonpham.memo.data.DbUtils;
import me.sonpham.memo.data.MemoContract;

/**
 * Created by sp on 3/20/15.
 */
public final class EditFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
	public static final String LOG_TAG = EditFragment.class.getSimpleName();
	public static final int DATA_LOADER = 1;

	@InjectView(R.id.fragment_edit_header) EditText mHeader;
	@InjectView(R.id.fragment_edit_body) EditText mBody;
	@InjectView(R.id.fragment_edit_date) EditText mDate;
	@InjectView(R.id.fragment_edit_time) EditText mTime;
	@InjectView(R.id.fragment_edit_location) EditText mLocation;
	@InjectView(R.id.progressBarIndeterminate) ProgressBarIndeterminate mProgressBar;
	private long mMemoId = -1;

	public EditFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_edit, container, false);
		ButterKnife.inject(this, rootView);

		mMemoId = getActivity().getIntent().getLongExtra(EditActivity.EXTRA_MEMO_ID, -1);
		mProgressBar.setVisibility(View.INVISIBLE);

		return rootView;
	}

	@OnClick(R.id.fragment_edit_save_button)
	public void save(View view) {
		new SaveMemoTask(mMemoId, mHeader.getText().toString(), mBody.getText().toString()
				, mDate.getText().toString(), mTime.getText().toString(), mLocation.getText().toString() ,mProgressBar).execute();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		getLoaderManager().initLoader(DATA_LOADER, null, this);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new DbCursorLoader(
				getActivity(),
				null,
				null,
				MemoContract.MemoEntry.COLUMN_ID + "=?",
				new String[] {String.valueOf(mMemoId)},
				null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		if (data != null && data.moveToFirst()) {
			mHeader.setText(data.getString(data.getColumnIndex(MemoContract.MemoEntry.COLUMN_HEADER)));
			mBody.setText(data.getString(data.getColumnIndex(MemoContract.MemoEntry.COLUMN_BODY)));
			mDate.setText(data.getString(data.getColumnIndex(MemoContract.MemoEntry.COLUMN_DATE)));
			mTime.setText(data.getString(data.getColumnIndex(MemoContract.MemoEntry.COLUMN_TIME)));
			mLocation.setText(data.getString(data.getColumnIndex(MemoContract.MemoEntry.COLUMN_LOCATION)));
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	}

	class SaveMemoTask extends AsyncTask<Void, Void, Boolean> {
		private long mId;
		private String mHeader, mBody, mDate, mTime, mLocation;
		private ProgressBarIndeterminate mProgressBar;

		public SaveMemoTask(long id, String header, String body, String date, String time, String location, ProgressBarIndeterminate progressBar) {
			super();
			mId = id;
			mHeader = header;
			mBody = body;
			mDate = date;
			mTime = time;
			mLocation = location;
			mProgressBar = progressBar;
		}

		@Override
		protected void onPreExecute() {
			mProgressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			ContentValues values = new ContentValues();
			values.put(MemoContract.MemoEntry.COLUMN_HEADER, mHeader);
			values.put(MemoContract.MemoEntry.COLUMN_BODY, mBody);
			values.put(MemoContract.MemoEntry.COLUMN_DATE, mDate);
			values.put(MemoContract.MemoEntry.COLUMN_TIME, mTime);
			values.put(MemoContract.MemoEntry.COLUMN_LOCATION, mLocation);
			//values.put(MemoContract.MemoEntry.COLUMN_TIMESTAMP, System.currentTimeMillis());

			if (mId != -1) {
				values.put(MemoContract.MemoEntry.COLUMN_ID, mId);
			}

			try {
				long _id;
				if (mId != -1) {
					_id = DbUtils.update(getActivity(), values, MemoContract.MemoEntry.COLUMN_ID + "=?", new String[] {String.valueOf(mId)});
				} else {
					_id = DbUtils.insertMemo(getActivity(), values);
				}
				if (_id != -1) {
					return true;
				}
			} catch (SQLException sqlE) {
				Log.e(LOG_TAG, "Failed to insert/update memo: " + sqlE.getClass());
				sqlE.printStackTrace();
				return false;
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean success) {
			mProgressBar.setVisibility(View.INVISIBLE);
			if (success) {
				Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
				getActivity().finish();
			} else {
				Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
