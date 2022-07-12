package com.skillbox.multithreading

import android.util.Log
import androidx.fragment.app.Fragment
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock

class LivelockFragment : Fragment() {
    private var i = 0
    private val lock1 = ReentrantLock()
    private val lock2 = ReentrantLock()

    override fun onResume() {
        super.onResume()

        val friend1 = Person("Вася")
        val friend2 = Person("Петя")


        val thread1 = Thread {
            Log.d("Deadlock", "Start1")

            (0..1000000).forEach {
                synchronized(lock1) {
                    try {
                        while (!lock2.isHeldByCurrentThread) {
                            Log.d("Deadlock", "Lock1 locked")
                        }
                        lock1.unlock()
                    } finally {
                        lock1.unlock()
                        lock2.unlock()
                    }
                }
            }
            Log.d("Deadlock", "End1 res $i")
        }

        val thread2 = Thread {
            Log.d("Deadlock", "Start2")
            (0..1000000).forEach {
                synchronized(lock2) {
                    try {
                        while (!lock1.isHeldByCurrentThread) {
                            Log.d("Deadlock", "Lock2 locked")

                        }
lock2.unlock()

                    } finally {
                        lock2.unlock()
                        lock1.unlock()
                    }
                }
            }

            Log.d("Deadlock", "End2 res $i")
        }

        thread1.start()
        thread2.start()

    }
}

