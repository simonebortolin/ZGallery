package com.mzelzoghbi.zgallery.activities;

import android.view.MenuItem;

import com.mzelzoghbi.zgallery.Constants;
import com.mzelzoghbi.zgallery.R;
import com.mzelzoghbi.zgallery.ZGallery;
import com.mzelzoghbi.zgallery.adapters.GridImagesAdapter;
import com.mzelzoghbi.zgallery.adapters.listeners.GridClickListener;
import com.mzelzoghbi.zgallery.entities.ZColor;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by mohamedzakaria on 8/6/16.
 */
public class ZGridActivity extends BaseActivity implements GridClickListener {
    private RecyclerView mRecyclerView;
    private GridImagesAdapter adapter;
    private int imgPlaceHolderResId;
    private int spanCount = 2;

    @Override
    protected int getResourceLayoutId() {
        return R.layout.z_activity_grid;
    }

    @Override
    protected void afterInflation() {
        mRecyclerView = findViewById(R.id.recyclerView);

        // get extra values
        imgPlaceHolderResId = getIntent().getIntExtra(Constants.IntentPassingParams.IMG_PLACEHOLDER, -1);
        spanCount = getIntent().getIntExtra(Constants.IntentPassingParams.COUNT, 2);

        adapter = new GridImagesAdapter(this, imageURLs, imgPlaceHolderResId);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(int pos) {

            try {
                ZGallery.with(this, imageURLs)
                        .setToolbarTitleColor(ZColor.WHITE)
                        .setToolbarColorResId(toolbarColorResId)
                        .setStatusbarColorResId(statusbarColorResId)
                        .setSelectedImgPosition(pos)
                        .setTitle(mToolbar.getTitle().toString())
                        .show();
                overridePendingTransition(R.anim.empty, R.anim.empty);
            } catch (Exception e) {

            }

    }
}
