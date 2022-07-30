package com.example.testapplication

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class CourseAdapter : AsyncListDifferDelegationAdapter<Course>(CourseAdapter.CourseDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(CourseAdapterDelegate())
    }
    class CourseDiffUtilCallback: DiffUtil.ItemCallback<Course>(){
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return when{
                oldItem is Course && newItem is Course -> oldItem.id == newItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return     oldItem == newItem
        }
    }

}