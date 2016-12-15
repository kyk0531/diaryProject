package me.sonpham.memo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;

public class SplashActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                overridePendingTransition(0, android.R.anim.fade_in);
                startActivity(new Intent(SplashActivity.this, ListActivity.class));
                finish();
            }
        }, 2000);
    }
}
