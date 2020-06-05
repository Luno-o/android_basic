package com.example.a06_viewandlayout

import android.annotation.TargetApi
import android.content.Context
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
        loginButton.isEnabled = false

        Glide.with(this)
            .load("https://syrlasu.kz/wp-content/uploads/2019/07/kak-razgovarivat-s-devushkoj-chtoby-ona-vlyubilas1-min.jpg")
            .into(imageView)

        edit_login.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginButton.isEnabled =
                    s?.let{ it.isNotEmpty() && edit_password.text.isNotEmpty() && checkbox.isChecked }
                        ?: false

            }
        })
        edit_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginButton.isEnabled =s?.let{it.isNotEmpty() && edit_login.text.isNotEmpty() && checkbox.isChecked }?:false
            }

        })
        checkbox.setOnClickListener {
            loginButton.isEnabled =
                edit_password.text.isNotEmpty() && edit_login.text.isNotEmpty() && checkbox.isChecked
        }
        loginButton.setOnClickListener {
            val progressBar = ProgressBar(this).apply {
                layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                    bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                    leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
                    rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
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
                Toast.makeText(this, "Registration success", Toast.LENGTH_SHORT).show()
            }, 2000)
        }

    }
}