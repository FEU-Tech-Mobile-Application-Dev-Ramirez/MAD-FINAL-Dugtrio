package com.example.mad_collaborative

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_page)

        // Initialize VideoView
        val videoView: VideoView = findViewById(R.id.videoView)

        // Corrected video path
        val videoPath = "android.resource://$packageName/${R.raw.working_out_video}"

        // Set the video URI and start playing
        videoView.setVideoURI(Uri.parse(videoPath))
        videoView.start()

        // Loop the video on completion
        videoView.setOnCompletionListener { videoView.start() }
    }
}
