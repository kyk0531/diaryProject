package me.sonpham.memo;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import me.sonpham.memo.data.MemoContract;

public class ShowActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private long mMemoId = -1;
    public static final String EXTRA_MEMO_ID = "memo_id";
    public static final int DATA_LOADER = 1;

    TextView mHeader;
    TextView mBody;
    TextView mDate;
    TextView mTime;
    TextView mLocation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        mMemoId = getIntent().getLongExtra(EditActivity.EXTRA_MEMO_ID, -1);

        mHeader = (TextView) findViewById(R.id.activity_show_header);
        mBody = (TextView) findViewById(R.id.activity_show_body);
        mDate = (TextView) findViewById(R.id.activity_show_date);
        mTime = (TextView) findViewById(R.id.activity_show_time);
        mLocation = (TextView) findViewById(R.id.activity_show_location);

        getLoaderManager().initLoader(DATA_LOADER, null, this);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new DbCursorLoader(
                this,
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_update) {
            startActivity(new Intent(this, EditActivity.class)
                    .putExtra(EditActivity.EXTRA_MEMO_ID, mMemoId));

            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
