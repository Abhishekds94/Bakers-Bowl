package com.abhishek.bakingapp;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhishek.bakingapp.model.Step;
import com.abhishek.bakingapp.utils.ConstantsUtil;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoPlayerFragment extends Fragment {

    public static final String STEP_URI =  "step_uri";
    public static final String STEP_PLAY_WINDOW_INDEX =  "step_play_window_index";
    public static final String STEP_PLAY_WHEN_READY =  "step_play_when_ready";
    public static final String STEP_SINGLE =  "step_single";
    public static final String STEP_VIDEO_POSITION =  "step_video_position";


    @BindView(R.id.player_view)
    SimpleExoPlayerView mPlayerView;

    @BindView(R.id.tv_step_title)
    TextView mStepTitle;

    @BindView(R.id.iv_video_placeholder)
    ImageView mImageViewPlaceholder;

    @BindView(R.id.tv_step_description)
    TextView mStepDescription;

    SimpleExoPlayer mSimpleExoPlayer;

    Uri mVideoUri;
    Bitmap mVideoThumbnailImage;
    Step mStep;
    int mWindowIndex;
    boolean mShouldPlayWhenReady = true;
    long mPlayerPosition;
    String mVideoThumbnail;


    public VideoPlayerFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_player, container, false);

        ButterKnife.bind(this, root);

        if(savedInstanceState != null){
            mShouldPlayWhenReady = savedInstanceState.getBoolean(STEP_PLAY_WHEN_READY);
            mPlayerPosition = savedInstanceState.getLong(STEP_VIDEO_POSITION);
            mStep = savedInstanceState.getParcelable(STEP_SINGLE);
            mVideoUri = Uri.parse(savedInstanceState.getString(STEP_URI));
            mWindowIndex = savedInstanceState.getInt(STEP_PLAY_WINDOW_INDEX);
        }
        else{
            if(getArguments() != null){

                mImageViewPlaceholder.setVisibility(View.GONE);
                mStep = getArguments().getParcelable(ConstantsUtil.STEP_SINGLE);
                mPlayerView.setVisibility(View.VISIBLE);


                if(mStep.getVideoURL().equals("")){
                    if(mStep.getThumbnailURL().equals("")){
                        mImageViewPlaceholder.setVisibility(View.VISIBLE);
                        mPlayerView.setUseController(false);
                        mPlayerView.setUseArtwork(true);
                    }
                    else{
                        mPlayerView.setVisibility(View.VISIBLE);
                        mVideoThumbnail = mStep.getThumbnailURL();
                        mImageViewPlaceholder.setVisibility(View.GONE);
                        mVideoThumbnailImage = ThumbnailUtils.createVideoThumbnail(mVideoThumbnail, MediaStore.Video.Thumbnails.MICRO_KIND);
                        mPlayerView.setDefaultArtwork(mVideoThumbnailImage);
                        mPlayerView.setUseArtwork(true);
                    }
                }
                else{
                    mVideoUri = Uri.parse(mStep.getVideoURL());
                }
            }
        }
        return root;
    }

    public void initializeVideoPlayer(Uri videoUri){

        mStepTitle.setText(mStep.getShortDescription());
        mStepDescription.setText(mStep.getDescription());

        if(mSimpleExoPlayer == null){

            mSimpleExoPlayer=  ExoPlayerFactory.newSimpleInstance(getActivity(),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());

            mPlayerView.setPlayer(mSimpleExoPlayer);

            String userAgent = Util.getUserAgent(getActivity(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(videoUri,
                    new DefaultDataSourceFactory(getActivity(), userAgent),
                    new DefaultExtractorsFactory(),
                    null,
                    null);

            if (mPlayerPosition != C.TIME_UNSET) {
                mSimpleExoPlayer.seekTo(mPlayerPosition);
            }
            mSimpleExoPlayer.prepare(mediaSource);
            mSimpleExoPlayer.setPlayWhenReady(mShouldPlayWhenReady);
        }
    }

    private void releasePlayer() {
        if (mSimpleExoPlayer != null) {
            updateStartPosition();
            mSimpleExoPlayer.stop();
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializeVideoPlayer(mVideoUri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || mSimpleExoPlayer == null) {
            initializeVideoPlayer(mVideoUri);
        }
        if(mSimpleExoPlayer != null){
            mSimpleExoPlayer.setPlayWhenReady(mShouldPlayWhenReady);
            mSimpleExoPlayer.seekTo(mPlayerPosition);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mSimpleExoPlayer != null){
            updateStartPosition();
            if (Util.SDK_INT <= 23) {
                releasePlayer();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mSimpleExoPlayer != null){
            updateStartPosition();
            if (Util.SDK_INT > 23) {
                releasePlayer();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        updateStartPosition();
        outState.putString(STEP_URI, mStep.getVideoURL());
        outState.putParcelable(STEP_SINGLE, mStep);
        outState.putLong(STEP_VIDEO_POSITION, mPlayerPosition);
        outState.putBoolean(STEP_PLAY_WHEN_READY, mShouldPlayWhenReady);
    }

    private void updateStartPosition() {
        if (mSimpleExoPlayer != null) {
            mShouldPlayWhenReady = mSimpleExoPlayer.getPlayWhenReady();
            mWindowIndex = mSimpleExoPlayer.getCurrentWindowIndex();
            mPlayerPosition = mSimpleExoPlayer.getCurrentPosition();
        }
    }

}