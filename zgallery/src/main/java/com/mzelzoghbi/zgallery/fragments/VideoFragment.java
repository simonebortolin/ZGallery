package com.mzelzoghbi.zgallery.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.mzelzoghbi.zgallery.R;

/**
 * Created by Marco on 08/03/2018.
 */

public class VideoFragment extends Fragment {

	String imageUrl;

	static EasyVideoCallback listener;

	public static Fragment newInstance(EasyVideoCallback list) {
		listener = list;

		return new VideoFragment();
	}

	private EasyVideoPlayer player;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View itemView = inflater.inflate(R.layout.z_pager_item, container,false);

		imageUrl = getArguments().getString("URL");

		final ImageView imageView = (ImageView) itemView.findViewById(R.id.iv);
		player = (EasyVideoPlayer) itemView.findViewById(R.id.player);

		player.setVisibility(View.VISIBLE);
		imageView.setVisibility(View.GONE);

		player.setCallback(new EasyVideoCallback() {
			@Override
			public void onStarted(EasyVideoPlayer player) {
				listener.onStarted(player);
			}

			@Override
			public void onPaused(EasyVideoPlayer player) {
				listener.onPaused(player);
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
				player.seekTo(0);
				listener.onCompletion(player);
			}

			@Override
			public void onRetry(EasyVideoPlayer player, Uri source) {

			}

			@Override
			public void onSubmit(EasyVideoPlayer player, Uri source) {

			}

			@Override
			public void onClickVideoFrame(EasyVideoPlayer player) {
				if (!player.isPlaying())
					player.start();

			}


		});

		// Sets the source to the HTTP URL held in the TEST_URL variable.
		// To play files, you can use Uri.fromFile(new File("..."))
		player.setSource(Uri.parse(imageUrl));


		return itemView;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (this.isVisible())
		{
			if (!isVisibleToUser)   // If we are becoming invisible, then...
			{
				player.pause();
			}
		}
	}
}
