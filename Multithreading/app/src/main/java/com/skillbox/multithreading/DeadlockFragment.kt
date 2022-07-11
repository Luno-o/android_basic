package com.skillbox.multithreading

import android.util.Log
import androidx.fragment.app.Fragment
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

class DeadlockFragment : Fragment() {

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
                    val locked1 = lock1.tryLock(10, TimeUnit.SECONDS)
                synchronized(lock1) {
                    try {
                        if (locked1) {
                            i++
                        }
                    } finally {
                        lock1.unlock()
                    }
                }
            }
                    Log.d("Deadlock", "End1 res $i")
        }

        val thread2 = Thread {
            Log.d("Deadlock", "Start2")
            (0..1000000).forEach {
                    val locked1 = lock1.tryLock(10, TimeUnit.SECONDS)
                synchronized(lock1) {
                    try {
                        if (locked1) {
                                i++
                        }


                    } finally {
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


data class Person(
    val name: String
) {

    fun throwBallTo(friend: Person) {
        synchronized(this) {
            Log.d(
                "Person",
                "$name бросает мяч ${friend.name} на потоке ${Thread.currentThread().name}"
            )
            Thread.sleep(500)
        }
        friend.throwBallTo(this)
    }

}
