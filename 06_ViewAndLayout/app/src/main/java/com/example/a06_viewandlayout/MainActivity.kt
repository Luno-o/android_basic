package com.example.a06_viewandlayout

import android.annotation.TargetApi
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
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
            Glide.with(this).load("https://goo.su/1FQ4").into(imageView)
        loginButton.isEnabled = false

        edit_login.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edit_login.text.isNotEmpty() && edit_password.text.isNotEmpty() && checkbox.isChecked){
                    loginButton.isEnabled = true
                }
            }

        })

        edit_password.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (edit_login.text.isNotEmpty() && edit_password.text.isNotEmpty() && checkbox.isChecked){
                    loginButton.isEnabled = true
                }
            }

        })

        checkbox.setOnClickListener { if (edit_login.text.isNotEmpty() && edit_password.text.isNotEmpty() && checkbox.isChecked){
            loginButton.isEnabled = true
        } }

        loginButton.setOnClickListener{
            val progressBar = ProgressBar(this).apply {
               layoutParams = ConstraintLayout.LayoutParams(
                   ConstraintLayout.LayoutParams.WRAP_CONTENT,
                   ConstraintLayout.LayoutParams.WRAP_CONTENT).apply {
                   bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                   topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                   rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
                   leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
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