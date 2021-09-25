package com.example.a06_viewandlayout

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.buttonANR
import kotlinx.android.synthetic.main.activity_main.checkbox
import kotlinx.android.synthetic.main.activity_main.edit_login
import kotlinx.android.synthetic.main.activity_main.edit_password
import kotlinx.android.synthetic.main.activity_main.image_view
import kotlinx.android.synthetic.main.activity_main.loginButton
import kotlinx.android.synthetic.main.activity_main.textViewLa


class LoginFragment : Fragment(R.layout.fragment_login) {

    val tag1 = "Main Activity"

    private var formState: FormState = FormState(false, "")
    override fun onActivityCreated(savedInstanceState: Bundle?) {


        super.onActivityCreated(savedInstanceState)
        DebugLogger.d(tag1, "OnCreate was called")
        if (savedInstanceState != null) {
            savedInstanceState.getParcelable<FormState>(KEY_VALUE) ?: error("Unexpected state")
        }

        Glide.with(this).load("https://goo.su/1FQ4").into(image_view)
        getfra.isEnabled = false
        val regexMail = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]")
        val regexPass = Regex("[a-zA-Z0-9]")
        buttonANR.setOnClickListener()
        {
            Thread.sleep(6000)
        }
        edit_login.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    loginButton.isEnabled =
                        edit_login.text.isNotEmpty() && edit_password.text.isNotEmpty() && checkbox.isChecked
                }

            })

        edit_password.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    loginButton.isEnabled =
                        edit_login.text.isNotEmpty() && edit_password.text.isNotEmpty() && checkbox.isChecked
                }

            })
        checkbox.setOnClickListener()
        {
            loginButton.isEnabled =
                edit_login.text.isNotEmpty() && edit_password.text.isNotEmpty() && checkbox.isChecked
        }
        loginButton.setOnClickListener()
        {
            val progressBar = ProgressBar(activity).apply {
                layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                ).apply {
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
                if (regexMail.containsMatchIn(edit_login.text)) {
                    if (regexPass.containsMatchIn(edit_password.text) && edit_password.text.toString().length > 6) {
                        formState.valid = true
                        val success = "Registration success"
                        textViewLa.text = success
                        formState.message = success
                        Toast.makeText(activity, success, Toast.LENGTH_SHORT).show()
                    } else {
                        val invalid = "Invalid password"
                        textViewLa.text = invalid
                        formState.message = invalid
                        Toast.makeText(activity, "Invalid password", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val invalid = "Invalid Login"
                    textViewLa.text = invalid
                    formState.message = invalid
                    Toast.makeText(activity, "Invalid login", Toast.LENGTH_SHORT).show()
                }
            }, 2000)


        }

    }

    override fun onPause() {
        super.onPause()
        DebugLogger.i(tag1, "onPause was called")
    }

    override fun onStart() {
        super.onStart()
        DebugLogger.d(tag1, "OnStart was called")
    }

    override fun onStop() {
        super.onStop()

        DebugLogger.w(tag1, "OnStop was called")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        formState =
            savedInstanceState?.getParcelable<FormState>(KEY_VALUE) ?: error("Unexpected state")
        textViewLa.text = formState.message
    }

    override fun onResume() {
        super.onResume()
        DebugLogger.v(tag1, "OnRes  ume was called")
    }

    override fun onDestroy() {
        DebugLogger.e(tag1, "onDestroy was called")
        super.onDestroy()
    }

    object DebugLogger {
        fun d(tag: String, message: String) {
            if (BuildConfig.DEBUG) {
                Log.d(tag, message)
            }
        }

        fun i(tag: String, message: String) {
            if (BuildConfig.DEBUG) {
                Log.i(tag, message)
            }
        }

        fun w(tag: String, message: String) {
            if (BuildConfig.DEBUG) {
                Log.w(tag, message)
            }
        }

        fun e(tag: String, message: String) {
            if (BuildConfig.DEBUG) {
                Log.e(tag, message)
            }
        }

        fun v(tag: String, message: String) {
            if (BuildConfig.DEBUG) {
                Log.v(tag, message)
            }
        }

    }

    companion object {
        private const val KEY_VALUE = "formState"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_VALUE, formState)

    }

}

