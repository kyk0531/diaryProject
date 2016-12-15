package me.sonpham.memo;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.sonpham.memo.data.MemoContract;

/**
 * Created by sp on 3/19/15.
 */
public class ListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
	public static final String LOG_TAG = ListFragment.class.getSimpleName();

	public static final int MEMO_LOADER = 0;

	private ListAdapter mListMemoAdapter;
	@InjectView(R.id.fragment_list) ListView listMemo;

	public ListFragment() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_list, container, false);
		ButterKnife.inject(this, rootView);
		mListMemoAdapter = new ListAdapter(getActivity(), null);

		listMemo = (ListView) rootView.findViewById(R.id.fragment_list);
		listMemo.setAdapter(mListMemoAdapter);
		listMemo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Cursor cursor = (Cursor) mListMemoAdapter.getItem(position);
				startActivity(new Intent(getActivity(), ShowActivity.class)
						.putExtra(
								ShowActivity.EXTRA_MEMO_ID,
								cursor.getLong(cursor.getColumnIndex(MemoContract.MemoEntry.COLUMN_ID))));
			}
		});

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		getLoaderManager().initLoader(MEMO_LOADER, null, this);
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new DbCursorLoader(getActivity(), null, null, null, null, null);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mListMemoAdapter.swapCursor(null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		mListMemoAdapter.swapCursor(data);
	}

	@Override
	public void onResume() {
		super.onResume();
		getLoaderManager().getLoader(MEMO_LOADER).onContentChanged();
	}
}
