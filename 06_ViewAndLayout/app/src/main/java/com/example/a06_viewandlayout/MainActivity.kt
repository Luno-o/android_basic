package com.example.a06_viewandlayout

import android.annotation.TargetApi
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.edit_login
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            val imageView = findViewById<ImageView>(R.id.image_view)
            Glide.with(this).load("https://lh3.googleusercontent.com/proxy/5i_P691R5xE9YTVnfzQSTP2WQPmY49JsaHY9SFEaAsvBTHLdmgAZedA4_E2Jbj6JG9mFCvGPgfM-RjcwuwAFITbvR1SvJhLtUF_Lp_lAzgA-08TEKdZw_NguNdNT4um_o_OXkvvgwkbxhLKun-s7XjeNnQ").into(imageView)
        loginButton.setOnClickListener{
            val progressBar = ProgressBar(this).apply {
               layoutParams = ConstraintLayout.LayoutParams(
                   ConstraintLayout.LayoutParams.WRAP_CONTENT,
                   ConstraintLayout.LayoutParams.WRAP_CONTENT).apply {
               }
            }
            container.addView(progressBar)
            loginButton.isEnabled = false
            edit_login.isEnabled = loginButton.isEnabled
            edit_password.isEnabled = loginButton.isEnabled
            checkbox.isEnabled = loginButton.isEnabled
            Handler().postDelayed({
                container.removeView(progressBar)
                loginButton.isEnabled = true
                edit_login.isEnabled = true
                edit_password.isEnabled = true
                checkbox.isEnabled = true
                Toast.makeText(this,"Registration success", Toast.LENGTH_SHORT).show()
            },2000)
        }

    }
}
//edit_login.text.isNotEmpty() && edit_password.text.isNotEmpty() && checkbox.isChecked