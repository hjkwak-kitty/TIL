package com.example.exoplayer

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager
import com.google.android.exoplayer2.drm.FrameworkMediaDrm
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory


class MainActivity : AppCompatActivity() {

  companion object {
    private const val USER_AGENT = "agent"
    private const val MP4_URL = "${BuildConfig.END_POINT}/h264_360.mp4"
    private const val HLS_URL = "${BuildConfig.END_POINT}/master.m3u8"
    private const val DASH_URL = "${BuildConfig.END_POINT}/sample.mpd"
    private const val DRM_DASH_URL = "${BuildConfig.END_POINT}/test2.mpd"
    private const val DRM_HLS_URL = "${BuildConfig.END_POINT}//master.m3u8"

    private const val key = "69eaa802a6763af979e8d1940fb88392"
    private const val base64EncodedKey = "aeqoAqZ2Ovl56NGUD7iDkg"
    private const val keyId = "abba271e8bcf552bbd2e86a434a9a5d9"
    private const val base64EncodedKeyId = "q7onHovPVSu9LoakNKml2Q"
  }

  enum class StreamingType {
    DASH, HLS, MP4
  }

  private val handler = Handler()
  private val bandwidthMeter = DefaultBandwidthMeter()
  private val drmCallback =  MyDRMCallBack(base64EncodedKeyId, base64EncodedKey)
  private val drmSessionManager = DefaultDrmSessionManager(C.CLEARKEY_UUID,FrameworkMediaDrm.newInstance(C.CLEARKEY_UUID), drmCallback, null, handler, null)
  private val selector = DefaultTrackSelector()
  private val loadControl = DefaultLoadControl()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val player = initPlayer(isDrm = true, type = StreamingType.DASH)

    player.playWhenReady = true
  }

  private fun initPlayer(isDrm: Boolean, type: StreamingType): SimpleExoPlayer {
    val renderersFactory = if (isDrm)
      DefaultRenderersFactory(this, drmSessionManager)
    else DefaultRenderersFactory(this)


    val player = ExoPlayerFactory.newSimpleInstance(renderersFactory, selector, loadControl)

    val playerView = findViewById<PlayerView>(R.id.player_view)
    playerView.player = player

    val dataSourceFactory = DefaultDataSourceFactory(this, bandwidthMeter,
        DefaultHttpDataSourceFactory(USER_AGENT, bandwidthMeter)
    )
    val mediaSource = when (type) {
      StreamingType.MP4 -> createHlsSource(if (isDrm) DRM_HLS_URL else MP4_URL, dataSourceFactory)
      StreamingType.DASH ->
        createDashSource(if (isDrm) DRM_DASH_URL else DASH_URL, dataSourceFactory)
      StreamingType.HLS -> createHlsSource(if (isDrm) DRM_HLS_URL else HLS_URL, dataSourceFactory)
    }

    player.prepare(mediaSource)

    return player
  }

  private fun createDashSource(url: String,
      dataSourceFactory: DefaultDataSourceFactory): DashMediaSource {
    return DashMediaSource.Factory(
        DefaultDashChunkSource.Factory(dataSourceFactory),
        dataSourceFactory
    ).createMediaSource(Uri.parse(url))
  }

  private fun createHlsSource(url: String,
      dataSourceFactory: DefaultDataSourceFactory): HlsMediaSource {
    return HlsMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(url))
  }


}
