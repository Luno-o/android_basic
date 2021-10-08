package com.example.a06_viewandlayout

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView = requireView().findViewById<ImageView>(R.id.image_view)
        Glide.with(this).load("https://goo.su/1FQ4").into(imageView)
        DebugLogger.d(tagFragment, "OnCreate was called")
        if (savedInstanceState != null) {
            savedInstanceState.getParcelable<FormState>(KEY_VALUE) ?: error("Unexpected state")
        }



        loginButton.isEnabled = false
        val regexMail = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]")
        val regexPass = Regex("[a-zA-Z0-9]")

        buttonANR.setOnClickListener {
            Thread.sleep(6000)
            Toast.makeText(context,"ANR but press", Toast.LENGTH_SHORT).show()
            DebugLogger.d(tagFragment,"ANR PRESS")
        }
        edit_login.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginButton.isEnabled =
                    edit_login.text.isNotEmpty() && edit_password.text.isNotEmpty() && checkbox.isChecked
            }

        })

        edit_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginButton.isEnabled =
                    edit_login.text.isNotEmpty() && edit_password.text.isNotEmpty() && checkbox.isChecked
            }

        })

        checkbox.setOnClickListener {
            loginButton.isEnabled =
                edit_login.text.isNotEmpty() && edit_password.text.isNotEmpty() && checkbox.isChecked
        }

        loginButton.setOnClickListener {
            val progressBar = ProgressBar(context).apply {
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
            loginContainer.addView(progressBar)
            DebugLogger.d(tagFragment,"login PRESS")
            loginButton.isEnabled = false
            edit_login.isEnabled = loginButton.isEnabled
            edit_password.isEnabled = loginButton.isEnabled
            checkbox.isEnabled = loginButton.isEnabled
            Handler().postDelayed({
              loginContainer.removeView(progressBar)
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
                          Toast.makeText(context, success, Toast.LENGTH_SHORT).show()
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.container,MainFragment())
                                .commit()

                    } else {
                        val invalid = "Invalid password"
                        textViewLa.text = invalid
                        formState.message = invalid
                             Toast.makeText(context, "Invalid password", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val invalid = "Invalid Login"
                    textViewLa.text = invalid
                    formState.message = invalid
                    Toast.makeText(context, "Invalid login", Toast.LENGTH_SHORT).show()
                }
            }, 2000)


        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DebugLogger.d(tagFragment,"onAttach")
    }
    companion object {

        private const val KEY_VALUE = "key_value"
        private const val KEY_TEXT = "key_text"

        fun newInstance(text: String): LoginFragment {
            return LoginFragment().withArguments { putString(KEY_TEXT, text) }
        }
    }

    private val tagFragment = "Fragment Activity"
    private var formState: FormState = FormState(false, "")


    override fun onPause() {
        super.onPause()
        DebugLogger.i(tagFragment, "onPause was called")
    }

    override fun onStart() {
        super.onStart()
        DebugLogger.d(tagFragment, "OnStart was called")
    }

    override fun onStop() {
        super.onStop()

        DebugLogger.w(tagFragment, "OnStop was called")
    }


    override fun onResume() {
        super.onResume()
        DebugLogger.v(tagFragment, "OnResume was called")
    }

    override fun onDestroy() {
        DebugLogger.e(tagFragment, "onDestroy was called")
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_VALUE, formState)
    }
}