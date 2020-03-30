package com.intanmarsjaf.bengongapps.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.intanmarsjaf.bengongapps.R
import com.intanmarsjaf.bengongapps.api.TopicAPI
import com.intanmarsjaf.bengongapps.model.Topic
import com.intanmarsjaf.bengongapps.retrofit_client.RetrofitClient
import com.intanmarsjaf.bengongapps.view_model.TopicViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.activity_topic_detail.*


class TopicDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_detail)
        val topicID = intent.getStringExtra("id")
        val topicName = intent.getStringExtra("title")
        val topicLink = intent.getStringExtra("link")
        val topicDesc = intent.getStringExtra("desc")
        findViewById<TextView>(R.id.topic_title).setText(topicName).toString()
        findViewById<TextView>(R.id.topic_desc).setText(topicDesc).toString()

        val youTubePlayerView: YouTubePlayerView = findViewById(R.id.player)
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val topicAPI = RetrofitClient.createRetrofit().create(TopicAPI::class.java)
                val topicViewModel = ViewModelProviders.of(this@TopicDetailActivity).get(TopicViewModel::class.java)
                topicViewModel?.topicAPI = topicAPI
                topicViewModel?.getTopicByCourse(topicID!!.toInt())

                topicViewModel.topics?.observe(this@TopicDetailActivity, Observer {
                        topic -> if (topic == null) {
                    print("Error")
                } else {
                    youTubePlayer.cueVideo(topicLink!!, 0f)
                }
                })
            }
        })

        player.getPlayerUiController().showFullscreenButton(true)

        player.getPlayerUiController().setFullScreenButtonClickListener(View.OnClickListener {
            if (player.isFullScreen()) {
                player.exitFullScreen()
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE

                if (supportActionBar != null) {
                    supportActionBar!!.show()
                }
            } else {
                player.enterFullScreen()
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

                if (supportActionBar != null) {
                    supportActionBar!!.hide()
                }
            }
        })
    }
}
