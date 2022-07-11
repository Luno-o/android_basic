package com.skillbox.multithreading

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_race_condition.*
//Race condition плозо воспроизводится когда инкремент меньше 50000
class RaceConditionFragment : Fragment(R.layout.fragment_race_condition) {
    private var resultValue = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        incrementNoSyncButton.setOnClickListener {
            val threadCount = threadCountEditText.text.toString().toIntOrNull()
            val incrementCount = incrementCountEditText.text.toString().toIntOrNull()
            if (incrementCount != null && threadCount != null){
            val expectedValue = resultValue + threadCount * incrementCount
            val currentTime = System.currentTimeMillis()

            (0 until threadCount).map {
                Thread {
                    Log.d(
                        "ThreadTest", "Thread start at " +
                                "${System.currentTimeMillis()} name ${Thread.currentThread().name}"
                    )
                    for (j in 0 until incrementCount) {
                        resultValue++
                    }
                    Log.d(
                        "ThreadTest", "Thread end at " +
                                "${System.currentTimeMillis()} name ${Thread.currentThread().name}"
                    )
                }.apply {
                    start()
                }
            }.map {
                it.join()
            }
            resultTextView.text = getString(
                R.string.result_value_text, expectedValue,
                resultValue, System.currentTimeMillis() - currentTime
            )
            }
        }
        incrementSyncButton.setOnClickListener {
            val threadCount = threadCountEditText.text.toString().toIntOrNull()
            val incrementCount = incrementCountEditText.text.toString().toIntOrNull()
            if (incrementCount != null && threadCount != null) {

                val expectedValue = resultValue + threadCount * incrementCount
                val currentTime = System.currentTimeMillis()
                (0 until threadCount).map {
                    Thread {
                        Log.d(
                            "ThreadTest", "Thread start at " +
                                    "${System.currentTimeMillis()} name ${Thread.currentThread().name}"
                        )
                        synchronized(this) {
                            for (j in 0 until incrementCount) {
                                resultValue++
                            }
                        }
                        Log.d(
                            "ThreadTest", "Thread end at " +
                                    "${System.currentTimeMillis()} name ${Thread.currentThread().name}"
                        )
                    }.apply {
                        start()
                    }
                }.map {
                    it.join()
                }
                resultTextView.text = getString(
                    R.string.result_value_text, expectedValue,
                    resultValue, System.currentTimeMillis() - currentTime
                )
            }

        }
    }

}