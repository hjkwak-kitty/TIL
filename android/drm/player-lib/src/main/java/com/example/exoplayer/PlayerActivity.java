/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.exoplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
import com.google.android.exoplayer2.drm.HttpMediaDrmCallback;
import com.google.android.exoplayer2.drm.LocalMediaDrmCallback;
import com.google.android.exoplayer2.drm.UnsupportedDrmException;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.android.exoplayer2.util.EventLogger;

import java.util.UUID;


/**
 * A fullscreen activity to play audio or video streams.
 */
public class PlayerActivity extends AppCompatActivity {

    private static String USER_AGENT = "agent";
    private static String MP4_URL = "${BuildConfig.END_POINT}/h264_360.mp4";
    private static String HLS_URL = "${BuildConfig.END_POINT}/master.m3u8";
    private static String DASH_URL = "${BuildConfig.END_POINT}/sample.mpd";
    private static String DRM_DASH_URL = "${BuildConfig.END_POINT}/test2.mpd";
    private static String DRM_HLS_URL = "${BuildConfig.END_POINT}//master.m3u8";

    enum StreamingType {DASH, HLS, MP4}

    PlayerView playerView;
    ExoPlayer player;
    boolean playWhenReady = true;
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        playerView = findViewById(R.id.video_view);
        initializePlayer();

    }

    String key = "69eaa802a6763af979e8d1940fb88392";
    String base64EncodedKey = "aeqoAqZ2Ovl56NGUD7iDkg";
    String keyId = "abba271e8bcf552bbd2e86a434a9a5d9";
    String base64EncodedKeyId = "q7onHovPVSu9LoakNKml2Q";



    /**
     * dash 스트리밍 플레이어 설정
     **/

    private void initializeDashPlayer() {
//    if (player == null) {
        // a factory to create an AdaptiveVideoTrackSelection
        TrackSelection.Factory adaptiveTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);

        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(adaptiveTrackSelectionFactory),
                new DefaultLoadControl());

        playerView.setPlayer(player);
        player.setPlayWhenReady(playWhenReady);

        Uri uri = Uri.parse("http://e2276f23.ngrok.io/media/sample.mpd");
        MediaSource mediaSource = buildDashMediaSource(uri);
        player.prepare(mediaSource, true, false);

//    }
    }

    private MediaSource buildDashMediaSource(Uri uri) {
        DataSource.Factory manifestDataSourceFactory = new DefaultHttpDataSourceFactory("ua");
        DashChunkSource.Factory dashChunkSourceFactory = new DefaultDashChunkSource.Factory(new DefaultHttpDataSourceFactory("ua", BANDWIDTH_METER));
        return new DashMediaSource.Factory(dashChunkSourceFactory,
                manifestDataSourceFactory).createMediaSource(uri);
    }

    //// 끝 /////

    /**
     * 로컬 mp4파일 drm설정
     **/
    DefaultTrackSelector trackSelector;
    EventLogger eventLogger;
    Handler mainHandler;

    private void initializePlayer() {
        TrackSelection.Factory adaptiveTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
        trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);
        eventLogger = new EventLogger(trackSelector);
        mainHandler = new Handler();
        @DefaultRenderersFactory.ExtensionRendererMode int extensionRendererMode = DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER;
        DrmSessionManager<FrameworkMediaCrypto> drmSessionManager = null;
        try {
            drmSessionManager = buildDrmSessionManager(this);
        } catch (UnsupportedDrmException e) {
            e.printStackTrace();
        }
        DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(this,
                drmSessionManager);

        player = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector);

        playerView.setPlayer(player);

        DataSource.Factory dataSourceFactory = getDataSourceFactory(this);
        MediaSource contentMediaSource =
                new ExtractorMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(dataSourceFactory.createDataSource().getUri());
//    player.addListener(new PlayerEventListener());
        player.addListener(eventLogger);
//    player.addMetadataOutput(eventLogger);
//    player.addAudioDebugListener(eventLogger);
//    player.addVideoDebugListener(eventLogger);
//    player.seekTo(contentPosition);
        player.setPlayWhenReady(true);
        player.prepare(contentMediaSource);

    }

    private DataSource.Factory getDataSourceFactory(Context context) {
        DataSource.Factory dataSourceFactory = null;
        DataSpec dataSpec = new DataSpec(RawResourceDataSource.buildRawResourceUri(R.raw.h264_360));
        final RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(context);
        try {
            rawResourceDataSource.open(dataSpec);
        } catch (RawResourceDataSource.RawResourceDataSourceException e) {
            e.printStackTrace();
        }

        dataSourceFactory = new DataSource.Factory() {
            @Override
            public DataSource createDataSource() {
                return rawResourceDataSource;
            }
        };
        return dataSourceFactory;
    }


    private DrmSessionManager<FrameworkMediaCrypto> buildDrmSessionManager(Context context) throws UnsupportedDrmException {
        DrmSessionManager<FrameworkMediaCrypto> drmSessionManager = null;
        Intent intent = ((Activity) context).getIntent();
        String drmScheme = "clearkey";
        UUID uuid = C.CLEARKEY_UUID;

        LocalMediaDrmCallback drmCallback =
                new LocalMediaDrmCallback(
                        ("{\"keys\":[{\"kty\":\"oct\",\"k\":\""
                                + base64EncodedKey
                                + "\",\"kid\":\""
                                + base64EncodedKeyId
                                + "\"}],\"type\":\"temporary\"}").getBytes());

        FrameworkMediaDrm fmDrm = FrameworkMediaDrm.newInstance(uuid);
        drmSessionManager = new DefaultDrmSessionManager<FrameworkMediaCrypto>(
                uuid,
                fmDrm,
                drmCallback,
                null,
                mainHandler,
                eventLogger);
        return drmSessionManager;
    }


    /**
     * mp4기본 재생 플레이어 설정
     **/
    private void initializeMp4Player() {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());

        playerView.setPlayer(player);

        player.setPlayWhenReady(playWhenReady);

        Uri uri = Uri.parse("http://e2276f23.ngrok.io/media/sample.mp4");
        MediaSource mediaSource = buildMediaSourceMp4(uri);
        player.prepare(mediaSource, true, false);
//    player.seekTo(currentWindow, playbackPosition);
    }

    private MediaSource buildMediaSourceMp4(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    //MP4 기본 재생 설정 완료//

}
