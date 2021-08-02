package com.afrakhteh.movies.ui.fragment.detail.trailer

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afrakhteh.movies.R
import com.afrakhteh.movies.databinding.CustomControllerBinding
import com.afrakhteh.movies.databinding.FragmentTrailersBinding
import com.afrakhteh.movies.ui.base.BaseFragment
import com.afrakhteh.movies.util.consts.KEYS
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.Player.STATE_BUFFERING
import com.google.android.exoplayer2.Player.STATE_READY
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.util.Util

class TrailersFragment : BaseFragment(), Player.EventListener {

    private lateinit var binding: FragmentTrailersBinding
    private lateinit var customControllerBinding: CustomControllerBinding
    private lateinit var trailer: String
    private lateinit var simpleExoPlayer: SimpleExoPlayer

    private var currentTime = 0F
    private var trailerDuration = 0F


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrailersBinding.inflate(inflater, container, false)
        customControllerBinding = CustomControllerBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getBundles()
        implementPlayer()
        buttonCliCk()

    }

    private fun buttonCliCk() {
        binding.trailerBackIv.setOnClickListener {
            val action = TrailersFragmentDirections.actionTrailersFragmentToHomeFragment()
            navigate(action)
        }
    }


    private fun implementPlayer() {
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(requireContext(),
                Util.getUserAgent(requireContext(), getString(R.string.app_name)))

        val mediaSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(trailer))

        simpleExoPlayer = SimpleExoPlayer.Builder(requireContext()).build()
        simpleExoPlayer.apply {
            setMediaSource(mediaSource)
            prepare()
            playWhenReady = true
            addListener(this@TrailersFragment)
        }
        binding.trailerPlayer.player = simpleExoPlayer

    }

    override fun onEvents(player: Player, events: Player.Events) {
        super.onEvents(player, events)
        if (events.containsAny(
                        Player.EVENT_IS_LOADING_CHANGED,
                        Player.EVENT_PLAYBACK_STATE_CHANGED,
                        Player.EVENT_PLAY_WHEN_READY_CHANGED,
                        Player.EVENT_IS_PLAYING_CHANGED
                )) {
            getTime(player)
        }
    }

    private fun getTime(player: Player) {
        currentTime = player.currentPosition.toFloat() /1000
        trailerDuration = player.contentDuration.toFloat() / 1000
        findRemainTime()
        findDuration()
    }

    private fun findDuration() {
        if (trailerDuration > 0L) {
            var min = (trailerDuration / 60).toInt()
            var sec = (trailerDuration % 60).toInt()

            setTotalTime(min, sec)
        }
    }

    private fun setTotalTime(min: Int, sec: Int) {
        customControllerBinding.exoDurationTv.text = "$min:${sec}"
    }

    private fun findRemainTime() {
        if (trailerDuration > 0L) {
            var remainTime = (trailerDuration - currentTime)
            var min = (remainTime / 60).toInt()
            var sec = (remainTime % 60).toInt()

            setRemainText(min, sec)
        }

    }

    private fun setRemainText(min: Int, sec: Int) {
        customControllerBinding.exoPositionTv.text = "$min:${String.format("%02d",sec)}"
    }

    override fun onPlaybackStateChanged(state: Int) {
        when (state) {
            STATE_BUFFERING -> {
                binding.trailerProgress.visibility = View.VISIBLE
            }
            STATE_READY -> {
                binding.trailerProgress.visibility = View.GONE
            }

        }
    }

    private fun getBundles() {
        trailer = arguments?.getString(KEYS.TRAILERS)!!
    }

    override fun onDestroy() {
        super.onDestroy()
        simpleExoPlayer.release()
        simpleExoPlayer.playWhenReady = false
        simpleExoPlayer.playbackState

    }

}