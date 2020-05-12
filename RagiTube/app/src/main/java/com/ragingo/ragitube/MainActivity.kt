package com.ragingo.ragitube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.youtube.player.YouTubeStandalonePlayer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiKey = getString(R.string.google_api_key)
        val videoId = "mw5VIEIvuMI"
        val intent = YouTubeStandalonePlayer.createVideoIntent(this, apiKey, videoId);
        startActivity(intent);
    }
}
