package com.viv.gunchung.bakewithkakgun.ui;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.viv.gunchung.bakewithkakgun.R;
import com.viv.gunchung.bakewithkakgun.models.RecipeStep;
import com.viv.gunchung.bakewithkakgun.utilities.BakingUtils;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * Created by Gunawan on 18/09/2017.
 */

public class StepDetailFragment extends Fragment {

    public static final String SELECTED_STEP_KEY = "selected_step";
    public static final String EXO_PLAYER_POSITION = "exo_player_position";
    public static final String VALID_VIDEO_URL = "exo_player_video_url";

    private RecipeStep mSelected;
    private String validVideoUrl;

    private SimpleExoPlayer mExoPlayer;

    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;

    private Unbinder unBinder;
    @BindView(R.id.exo_player)
    SimpleExoPlayerView mPlayerView;
    @BindView(R.id.tv_step_detail_desc)
    TextView stepTextView;

    public StepDetailFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Timber.d("onActivityCreated start");
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            mSelected = Parcels.unwrap(savedInstanceState.getParcelable(SELECTED_STEP_KEY));
            playbackPosition = savedInstanceState.getLong(EXO_PLAYER_POSITION);
        }

        stepTextView.setText(mSelected.getDescription());

        if (BakingUtils.isValidUrl(mSelected.getVideoURL())) {
            Timber.d("getVideoURL " + mSelected.getVideoURL());
            mPlayerView.setVisibility(View.VISIBLE);
            validVideoUrl = mSelected.getVideoURL();
            initializePlayer();
        } else if (BakingUtils.isValidUrl(mSelected.getThumbnailURL())) {
            Timber.d("getThumbnailURL " + mSelected.getThumbnailURL());
            mPlayerView.setVisibility(View.VISIBLE);
            validVideoUrl = mSelected.getThumbnailURL();
            initializePlayer();
        } else {
            Timber.d("NO VIDEOOOOOO");
            mPlayerView.setVisibility(View.GONE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Timber.d("onCreateView start");

        final View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        unBinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    public void initializePlayer() {
        Timber.d("initializePlayer start");
        if (mExoPlayer == null && BakingUtils.isValidUrl(validVideoUrl)) {
            Timber.d("mExoPlayer == null");
            Uri mediaUri = Uri.parse(validVideoUrl);

            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(getActivity(), "Udacity");

            MediaSource mediaSource = new ExtractorMediaSource(mediaUri,
                    new DefaultDataSourceFactory(getActivity(), userAgent),
                    new DefaultExtractorsFactory(), null, null);

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(playWhenReady);
            mExoPlayer.seekTo(currentWindow, playbackPosition);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            playbackPosition = mExoPlayer.getCurrentPosition();
            currentWindow = mExoPlayer.getCurrentWindowIndex();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    public void onResume() {
        super.onResume();
        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUi();
        }
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBinder.unbind();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(EXO_PLAYER_POSITION, playbackPosition);
        outState.putParcelable(SELECTED_STEP_KEY, Parcels.wrap(mSelected));
    }

    private void hideSystemUi() {
        mPlayerView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    public void setSelectedStep(RecipeStep mSelected) {
        this.mSelected = mSelected;
    }
}
