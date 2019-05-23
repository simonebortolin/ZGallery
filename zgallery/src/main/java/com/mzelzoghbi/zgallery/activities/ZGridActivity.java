package com.mzelzoghbi.zgallery.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.mzelzoghbi.zgallery.Constants;
import com.mzelzoghbi.zgallery.R;
import com.mzelzoghbi.zgallery.ZGallery;
import com.mzelzoghbi.zgallery.adapters.GridImagesAdapter;
import com.mzelzoghbi.zgallery.adapters.listeners.GridClickListener;
import com.mzelzoghbi.zgallery.entities.ZColor;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by mohamedzakaria on 8/6/16.
 */
public final class ZGridActivity extends BaseActivity implements GridClickListener {

    private LinearLayout connectionStatus;

    private RecyclerView mRecyclerView;
    private GridImagesAdapter adapter;

    private int imgPlaceHolderResId;
    private int spanCount = 2;

    private boolean scrollGridLoading = false;
    private Integer prevTotalElementsInGrid = 0;

    private BroadcastReceiver refreshMediaGridReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.hasExtra("newImageURLs")) {
                ArrayList<String> newImageUrls = intent.getStringArrayListExtra("newImageURLs");
                imageURLs.clear();
                imageURLs.addAll(newImageUrls);
                adapter.notifyDataSetChanged();
                connectionStatus.setVisibility(GONE);
            } else if (intent.hasExtra("imageURLsToAdd")) {
                ArrayList<String> newImageUrls = intent.getStringArrayListExtra("imageURLsToAdd");
                imageURLs.addAll(newImageUrls);
                adapter.notifyDataSetChanged();
                connectionStatus.setVisibility(GONE);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter filter = new IntentFilter("me.vms.ZGALLERY_UPDATE");
        registerReceiver(refreshMediaGridReceiver, filter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) { //dy is the amount of vertical scroll (?)

                //Questo evento parte molto frequentemente quindi utilizzo le variabili loading e prevTotalElementsInGrid per assicurarmi di
                //fare una nuova richiesta solo una volta e solo in un preciso momento
                if (scrollGridLoading) {
                    if (imageURLs.size() > prevTotalElementsInGrid) {
                        scrollGridLoading = false;
                        prevTotalElementsInGrid = imageURLs.size();
                    }
                }
                //Quando mi trovo a metà dello scorrimento della lista inizio ad aggiornare i nuovi elementi
                else if (!scrollGridLoading && (((GridLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition() > imageURLs.size() / 2)) {
                    scrollGridLoading = true;

                    if(isOnline()) {
                        //Carico altre immagini
                        Intent intent = new Intent();
                        intent.setAction("me.vms.MEDIA_UPDATE");
                        intent.putExtra("onScroll", true);
                        getApplicationContext().sendBroadcast(intent);
                    } else {
                        scrollGridLoading = false;
                        connectionStatus.setVisibility(VISIBLE);
                    }
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @Override
    protected int getResourceLayoutId() {
        return R.layout.z_activity_grid;
    }

    @Override
    protected void afterInflation() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // get extra values
        imgPlaceHolderResId = getIntent().getIntExtra(Constants.IntentPassingParams.IMG_PLACEHOLDER, -1);
        spanCount = getIntent().getIntExtra(Constants.IntentPassingParams.COUNT, 2);

        connectionStatus = findViewById(R.id.connection_status);

        if(!isOnline()) {
            connectionStatus.setVisibility(VISIBLE);
        } else {
            connectionStatus.setVisibility(GONE);
        }

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

        ZGallery.with(this, imageURLs)
                .setToolbarTitleColor(ZColor.WHITE)
                .setToolbarColorResId(toolbarColorResId)
                .setSelectedImgPosition(pos)
                .setTitle(mToolbar.getTitle().toString())
                .show();
    }

    public void ReloadMediaGrid(View view) {
        Intent intent = new Intent();
        intent.setAction("me.vms.MEDIA_UPDATE");
        getApplicationContext().sendBroadcast(intent);
    }
}
