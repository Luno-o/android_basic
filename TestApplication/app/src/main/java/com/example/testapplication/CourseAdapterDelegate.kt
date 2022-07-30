package com.example.testapplication

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_course.view.*

class CourseAdapterDelegate: AbsListItemAdapterDelegate<Course,Course,CourseAdapterDelegate.Holder>() {


    class Holder(override val containerView: View):RecyclerView.ViewHolder(containerView), LayoutContainer {
fun bind(course: Course){
    containerView.idCourseTextView.text = course.id.toString()
    containerView.titleCourseTextView.text = course.title
}
    }

    override fun isForViewType(item: Course, items: MutableList<Course>, position: Int): Boolean {
        return item is Course
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_course))
    }

    override fun onBindViewHolder(item: Course, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }
}