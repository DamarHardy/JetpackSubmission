package com.damar.jetpacksubmission.ui.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.lifecycleScope
import com.damar.jetpacksubmission.R
import com.damar.jetpacksubmission.databinding.ActivitySplashScreenBinding
import com.damar.jetpacksubmission.ui.MainActivity
import com.damar.jetpacksubmission.utils.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@Suppress("DEPRECATION")
@AndroidEntryPoint
class SplashScreen : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels()
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var customListener: MotionLayout.TransitionListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        lifecycleScope.launchWhenCreated {
            viewModel.init()
        }
        customListener = object : MotionLayout.TransitionListener{
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) { }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                if(viewModel.state.value == DataState.Loading){
                    startMiddleAnim()
                }else{
                    if(viewModel.state.value != null){
                        val intent = Intent(this@SplashScreen, MainActivity::class.java)
                        this@SplashScreen.startActivity(intent)
                        this@SplashScreen.finish()
                    }
                }
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) { }
        }
        startMiddleAnim()
        binding.splashMotion.setTransitionListener(customListener)
        viewModel.state.observe(this, {
            if(it!=null){
                startMiddleAnim()
            }
        })
        setContentView(binding.root)
    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUIAndNavigation(this)
        }
    }
    private fun startMiddleAnim(){
        binding.splashMotion.setTransition(R.id.start, R.id.middle)
        binding.splashMotion.transitionToEnd()
    }
    private fun hideSystemUIAndNavigation(activity: Activity) {
        val decorView: View = activity.window.decorView
        decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_IMMERSIVE
                    // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}