package com.damar.jetpacksubmission.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.damar.jetpacksubmission.R
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
    private fun println(s: String){
        Log.d(TAG, s)
    }
    fun setToolbar(toolbar: Toolbar?, isHome: Boolean){
        try{
            if(toolbar!=null){
                setSupportActionBar(toolbar)
                if(isHome){
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }else{
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
            }
        }catch (e: Exception){
            println("${e.message}")
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}