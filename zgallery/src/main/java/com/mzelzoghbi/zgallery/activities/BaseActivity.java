package com.mzelzoghbi.zgallery.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.mzelzoghbi.zgallery.Constants;
import com.mzelzoghbi.zgallery.R;

import java.util.ArrayList;

/**
 * Created by mohamedzakaria on 8/11/16.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Toolbar mToolbar;
    protected ArrayList<String> imageURLs;
    protected int toolbarTitleColor;
    protected int toolbarColorResId;
    private String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResourceLayoutId());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        // get values
        imageURLs = getIntent().getStringArrayListExtra(Constants.IntentPassingParams.IMAGES);
        toolbarColorResId = getIntent().getIntExtra(Constants.IntentPassingParams.TOOLBAR_COLOR_ID, -1);
        title = getIntent().getStringExtra(Constants.IntentPassingParams.TITLE);
        toolbarTitleColor = getIntent().getIntExtra(Constants.IntentPassingParams.TOOLBAR_TITLE_COLOR, -1);
//        toolbarUpI R.drawable.ic_arrow_back_black

        if (getSupportActionBar() == null) {
            setSupportActionBar(mToolbar);
            mToolbar.setVisibility(View.VISIBLE);

            mToolbar.setTitleTextColor(toolbarTitleColor);
            Drawable icon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_black);
            if (icon != null) {
                icon.setTint(toolbarTitleColor);
            }
            getSupportActionBar().setHomeAsUpIndicator(icon);

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


    protected abstract int getResourceLayoutId();

    protected abstract void afterInflation();

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}
