package com.jkdajac.probniy

import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    var anim : AnimationDrawable? = null
    var player : MediaPlayer? = null

    private val job = Job()

     override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        player = MediaPlayer.create(this, R.raw.happybirthday)

        ivMainImage.setBackgroundResource(R.drawable.anim_slide)
        anim = ivMainImage.background as AnimationDrawable?

        async {
            delay(1000)
            animHappy()
        }

        async {
            delay(2000)
            animBubbleLeft()
        }

        async {
            delay(3000)
            animBubbleRight()
        }

        btExit.setOnClickListener {
            finishAffinity()
        }

        btStart.setOnClickListener {
            anim?.start()
            play()
        }

        btStop.setOnClickListener {
            anim?.stop()
            stop()
        }
    }

    fun animHappy(){
        val animation = AnimationUtils.loadAnimation(this, R.anim.anim_happy)
        ivHappy.startAnimation(animation)
    }

    fun animBubbleLeft(){
        val animation = AnimationUtils.loadAnimation(this, R.anim.anim_bubble_left)
        ivBubbleLeft.startAnimation(animation)
    }

    fun animBubbleRight(){
        val animation = AnimationUtils.loadAnimation(this, R.anim.anim_bubble_right)
        ivBubbleRight.startAnimation(animation)
    }

    fun stopPlay(){
        player?.stop()
        btStop.isEnabled = false
        player?.prepare()
        player?.seekTo(0)
        btStart.isEnabled(true)
    }
    fun play(){
        player?.start()
        btStart.isEnabled(false)
        btStop.isEnabled(true)
    }
    fun stop(){
        player?.stop()

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        if (player?.isPlaying!!){
            stopPlay()
        }
    }
}
private fun Button.isEnabled(b: Boolean) {

}

