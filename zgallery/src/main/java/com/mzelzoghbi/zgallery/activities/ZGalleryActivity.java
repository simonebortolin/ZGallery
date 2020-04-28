package com.mzelzoghbi.zgallery.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mzelzoghbi.zgallery.Constants;
import com.mzelzoghbi.zgallery.CustomViewPager;
import com.mzelzoghbi.zgallery.OnImgClick;
import com.mzelzoghbi.zgallery.R;
import com.mzelzoghbi.zgallery.adapters.HorizontalListAdapters;
import com.mzelzoghbi.zgallery.adapters.ViewPagerAdapter;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by mohamedzakaria on 8/11/16.
 */
public class ZGalleryActivity extends BaseActivity {
    private RelativeLayout mainLayout;

    private LinearLayout connectionStatus;

    CustomViewPager mViewPager;
    ViewPagerAdapter adapter;
    RecyclerView imagesHorizontalList;
    LinearLayoutManager mLayoutManager;
    HorizontalListAdapters hAdapter;
    private int currentPos;
    private int bgColor;

    private boolean scrollGridLoading = false;
    private Integer prevTotalElementsInGrid = 0;

    private BroadcastReceiver refreshMediaGalleryReceiver = new BroadcastReceiver() {
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
    public void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter("me.vms.ZGALLERY_UPDATE");
        registerReceiver(refreshMediaGalleryReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();

        unregisterReceiver(refreshMediaGalleryReceiver);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imagesHorizontalList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) { //dx is the amount of horizontal scroll

                //Questo evento parte molto frequentemente quindi utilizzo le variabili loading e prevTotalElementsInGrid per assicurarmi di
                //fare una nuova richiesta solo una volta e solo in un preciso momento
                if (scrollGridLoading) {
                    if (imageURLs.size() > prevTotalElementsInGrid) {
                        scrollGridLoading = false;
                        prevTotalElementsInGrid = imageURLs.size();
                    }
                }
                //Quando mi trovo a metà dello scorrimento della lista inizio ad aggiornare i nuovi elementi
                else if (!scrollGridLoading && (((LinearLayoutManager) imagesHorizontalList.getLayoutManager()).findFirstVisibleItemPosition() > imageURLs.size() / 2)) {
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
        return R.layout.z_activity_gallery;
    }

    @Override
    protected void afterInflation() {
        // init layouts
        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        mViewPager = (CustomViewPager) findViewById(R.id.pager);
        imagesHorizontalList = (RecyclerView) findViewById(R.id.imagesHorizontalList);

        // get intent data
        currentPos = getIntent().getIntExtra(Constants.IntentPassingParams.SELECTED_IMG_POS, 0);
        bgColor = getIntent().getIntExtra(Constants.IntentPassingParams.BG_COLOR, -1);

        mainLayout.setBackgroundColor(bgColor);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        connectionStatus = findViewById(R.id.connection_status);

        if(!isOnline()) {
            connectionStatus.setVisibility(VISIBLE);
        } else {
            connectionStatus.setVisibility(GONE);
        }

        // pager adapter
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), this, imageURLs, mToolbar, imagesHorizontalList, connectionStatus);
        mViewPager.setAdapter(adapter);
        // horizontal list adapter
        hAdapter = new HorizontalListAdapters(this, imageURLs, new OnImgClick() {
            @Override
            public void onClick(int pos) {
                mViewPager.setCurrentItem(pos, true);
            }
        });
        imagesHorizontalList.setLayoutManager(mLayoutManager);
        imagesHorizontalList.setAdapter(hAdapter);
        hAdapter.notifyDataSetChanged();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                imagesHorizontalList.smoothScrollToPosition(position);
                hAdapter.setSelectedItem(position);
                if(ViewPagerAdapter.isVideoFile(imageURLs.get(position))){
                    imagesHorizontalList.setVisibility(View.GONE);
                } else {
                    imagesHorizontalList.setVisibility(adapter.isShowing ? View.VISIBLE : View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        hAdapter.setSelectedItem(currentPos);
        mViewPager.setCurrentItem(currentPos);
        if(ViewPagerAdapter.isVideoFile(imageURLs.get(currentPos))){
            imagesHorizontalList.setVisibility(View.GONE);
        } else {
            imagesHorizontalList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void ReloadMediaGallery(View view) {
        Intent intent = new Intent();
        intent.setAction("me.vms.MEDIA_UPDATE");
        getApplicationContext().sendBroadcast(intent);
    }
}
