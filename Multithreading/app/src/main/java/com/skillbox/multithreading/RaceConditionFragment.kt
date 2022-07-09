package com.skillbox.multithreading

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_race_condition.*

class RaceConditionFragment : Fragment(R.layout.fragment_race_condition) {
    private var resultValue = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        incrementNoSyncButton.setOnClickListener {
            val threadCount = threadCountEditText.text.toString().toInt()
            val incrementCount = incrementCountEditText.text.toString().toInt()
            val expectedValue = resultValue +threadCount*incrementCount
             (0 until threadCount).map{
                Thread{
                    for (j in 0 until incrementCount){
                    resultValue++
                    }
                }.apply {
                    start()
                }
            }.map { it.join() }
                    resultTextView.text = getString(R.string.result_value_text,resultValue,
                        expectedValue)
        }
        incrementSyncButton.setOnClickListener {

        }
    }

}