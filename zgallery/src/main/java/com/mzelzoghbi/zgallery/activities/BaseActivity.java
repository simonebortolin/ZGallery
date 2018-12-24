package com.mzelzoghbi.zgallery.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.mzelzoghbi.zgallery.Constants;
import com.mzelzoghbi.zgallery.R;
import com.mzelzoghbi.zgallery.entities.ZColor;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

/**
 * Created by mohamedzakaria on 8/11/16.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Toolbar mToolbar;
    protected ArrayList<String> imageURLs;
    protected ZColor toolbarTitleColor;
    protected int toolbarColorResId;
    protected int statusbarColorResId;

    private String title;

    private boolean resumed = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResourceLayoutId());

        mToolbar = findViewById(R.id.toolbar);

        // get values
        imageURLs = getIntent().getStringArrayListExtra(Constants.IntentPassingParams.IMAGES);
        toolbarColorResId = getIntent().getIntExtra(Constants.IntentPassingParams.TOOLBAR_COLOR_ID, -1);
        statusbarColorResId = getIntent().getIntExtra(Constants.IntentPassingParams.STATUSBAR_COLOR_ID, -1);

        title = getIntent().getStringExtra(Constants.IntentPassingParams.TITLE);
        toolbarTitleColor = (ZColor) getIntent().getSerializableExtra(Constants.IntentPassingParams.TOOLBAR_TITLE_COLOR);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && statusbarColorResId != -1) {
            getWindow().setStatusBarColor(getResources().getColor(statusbarColorResId));
        }

        if (getSupportActionBar() == null) {
            setSupportActionBar(mToolbar);
            mToolbar.setVisibility(View.VISIBLE);
            if (toolbarTitleColor == ZColor.BLACK) {
                mToolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.black));
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black);
            } else {
                mToolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
            }
            mToolbar.setBackgroundColor(getResources().getColor(toolbarColorResId));
            if (title != null) {
                getSupportActionBar().setTitle(title);
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        } else {
            mToolbar.setVisibility(View.GONE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        afterInflation();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onRestart() {
        resumed = true;
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
        overridePendingTransition(R.anim.empty, R.anim.empty);

    }

    protected abstract int getResourceLayoutId();

    protected abstract void afterInflation();
}
