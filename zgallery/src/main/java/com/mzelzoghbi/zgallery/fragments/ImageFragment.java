package com.mzelzoghbi.zgallery.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mzelzoghbi.zgallery.R;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Marco on 08/03/2018.
 */

public class ImageFragment extends Fragment {

	String imageUrl;

	PhotoViewAttacher mPhotoViewAttacher;

	static View.OnClickListener listener;

	public static Fragment newInstance(View.OnClickListener list) {
		listener = list;

		return new ImageFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View itemView = inflater.inflate(R.layout.z_pager_item, container,false);

		imageUrl = getArguments().getString("URL");

		final ImageView imageView = (ImageView) itemView.findViewById(R.id.iv);
		final EasyVideoPlayer player = (EasyVideoPlayer) itemView.findViewById(R.id.player);

		player.setVisibility(View.GONE);
		imageView.setVisibility(View.VISIBLE);

		Glide.with(this).load(imageUrl).listener(new RequestListener<Drawable>() {
			@Override
			public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
				return false;
			}

			@Override
			public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
				mPhotoViewAttacher = new PhotoViewAttacher(imageView);

				mPhotoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
					@Override
					public void onPhotoTap(View view, float x, float y) {
						listener.onClick(view);
					}

					@Override
					public void onOutsidePhotoTap() {

					}
				});

				return false;
			}
		}).into(imageView);

		return itemView;
	}
}
