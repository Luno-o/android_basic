package com.example.flowexample.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.RadioGroup
import androidx.annotation.LayoutRes
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber
fun EditText.textChangeFlow(): Flow<String>{
    return callbackFlow<String> {
        val textChangeListener = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                trySendBlocking(p0.toString())
            }
            override fun afterTextChanged(p0: Editable?) {

            }
        }
        this@textChangeFlow.addTextChangedListener(textChangeListener)
        awaitClose {
            Timber.d("awaitClose")
            this@textChangeFlow.removeTextChangedListener(textChangeListener)
        }
    }
}

fun CheckBox.checkedChangesFlow():Flow<Boolean>{
    return callbackFlow {
        val checkedChangeListener = CompoundButton.OnCheckedChangeListener { _, isChecked ->
trySendBlocking(isChecked)
        }
        setOnCheckedChangeListener(checkedChangeListener)
        awaitClose{
            setOnCheckedChangeListener(null)
        }
    }
}
 fun RadioGroup.checkedChangesFlow():Flow<Int>{
     return callbackFlow<Int> {
         val checkedChangeListener = RadioGroup.OnCheckedChangeListener { radioGroup, i ->
trySendBlocking(i)
         }
         this@checkedChangesFlow.setOnCheckedChangeListener(checkedChangeListener)
         awaitClose {
             Timber.d("awaitClose radio group")
             setOnCheckedChangeListener(null)
         }
     }
 }
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean= false): View {
    return LayoutInflater.from(context).inflate(layoutRes,this,attachToRoot)
}
