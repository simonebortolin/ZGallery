package com.mzelzoghbi.zgallery.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.mzelzoghbi.zgallery.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

/**
 * Created by mohamedzakaria on 8/11/16.
 */
public class ViewPagerAdapter extends PagerAdapter {

    Activity activity;
    LayoutInflater mLayoutInflater;
    ArrayList<String> images;
    PhotoViewAttacher mPhotoViewAttacher;
    private boolean isShowing = true;
    private Toolbar toolbar;
    private RecyclerView imagesHorizontalList;

    public ViewPagerAdapter(Activity activity, ArrayList<String> images, Toolbar toolbar, RecyclerView imagesHorizontalList) {
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
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.z_pager_item, container, false);


        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    Glide.get(ViewPagerAdapter.this.activity).clearDiskCache();

                    return null;
                }
            }.execute().get();
        } catch (InterruptedException e) {
            //e.printStackTrace();
        } catch (ExecutionException e) {
            //e.printStackTrace();
        }
        Glide.get(ViewPagerAdapter.this.activity).clearMemory();



        final ImageView imageView = itemView.findViewById(R.id.imageView);
        final ProgressBar progressBar = itemView.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        Glide.with(activity).load(images.get(position)).listener(new RequestListener<Drawable>() {

            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);

                mPhotoViewAttacher = new PhotoViewAttacher(imageView);

                mPhotoViewAttacher.setOnPhotoTapListener(new OnPhotoTapListener() {
                    @Override
                    public void onPhotoTap(ImageView view, float x, float y) {

                        if (isShowing) {
                            isShowing = false;
                            toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
                            imagesHorizontalList.animate().translationY(imagesHorizontalList.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
                        } else {
                            isShowing = true;
                            toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                            imagesHorizontalList.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                        }
                    }
                });


                return false;
            }

        }).into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
