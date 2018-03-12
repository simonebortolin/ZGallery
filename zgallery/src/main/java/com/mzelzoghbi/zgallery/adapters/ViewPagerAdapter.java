package com.mzelzoghbi.zgallery.adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.mzelzoghbi.zgallery.fragments.ImageFragment;
import com.mzelzoghbi.zgallery.fragments.VideoFragment;

import java.net.URLConnection;
import java.util.ArrayList;


/**
 * Created by mohamedzakaria on 8/11/16.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    Activity activity;
    LayoutInflater mLayoutInflater;
    ArrayList<String> images;
    public boolean isShowing = true;
    private Toolbar toolbar;
    private RecyclerView imagesHorizontalList;

    public ViewPagerAdapter(FragmentManager fm, Activity activity, ArrayList<String> images, Toolbar toolbar, RecyclerView imagesHorizontalList) {
        super(fm);
        this.activity = activity;
        mLayoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.images = images;
        this.toolbar = toolbar;
        this.imagesHorizontalList = imagesHorizontalList;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Fragment getItem(final int position) {
        if (!isVideoFile(images.get(position))) {
            ImageFragment im = (ImageFragment) ImageFragment.newInstance(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isShowing) {
                        isShowing = false;
                        toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
                        imagesHorizontalList.animate().translationY(imagesHorizontalList.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
                    } else {
                        isShowing = true;
                        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                        imagesHorizontalList.setVisibility(View.VISIBLE);
                        imagesHorizontalList.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                    }
                }
            });

            Bundle bundl = new Bundle();
            bundl.putString("URL", images.get(position));

            im.setArguments(bundl);

            return im;
        } else {
            VideoFragment im = (VideoFragment) VideoFragment.newInstance(new EasyVideoCallback() {
                @Override
                public void onStarted(EasyVideoPlayer player) {
                    if (isShowing) {
                        isShowing = false;
                        toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
                    }
                }

                @Override
                public void onPaused(EasyVideoPlayer player) {
                    if (!isShowing) {
                        isShowing = true;
                        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                    }
                }

                @Override
                public void onPreparing(EasyVideoPlayer player) {

                }

                @Override
                public void onPrepared(EasyVideoPlayer player) {

                }

                @Override
                public void onBuffering(int percent) {

                }

                @Override
                public void onError(EasyVideoPlayer player, Exception e) {

                }

                @Override
                public void onCompletion(EasyVideoPlayer player) {
                    if (!isShowing) {
                        isShowing = true;
                        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                    }
                }

                @Override
                public void onRetry(EasyVideoPlayer player, Uri source) {

                }

                @Override
                public void onSubmit(EasyVideoPlayer player, Uri source) {

                }

                @Override
                public void onClickVideoFrame(EasyVideoPlayer player) {
                }
            });

            Bundle bundl = new Bundle();
            bundl.putString("URL", images.get(position));

            im.setArguments(bundl);

            return im;
        }
    }

    public static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }

    public static boolean isVideoFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("video");
    }
}
