package com.example.contentprovider.presentation.detailInfo

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.contentprovider.R
import com.example.contentprovider.custom_content_provider.Course
import com.example.contentprovider.custom_content_provider.SkillboxContentProvider
import com.example.contentprovider.data.Contact
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment(R.layout.fragment_detail) {
private val viewModel: DetailFragmentViewModel by viewModels()
    private val args:DetailFragmentArgs by navArgs()
    private val uriProviderCourses = "com.example.contentprovider.provider/courses"
    private val uriProvider = Uri.parse("content://$uriProviderCourses")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllCourses()
        viewModel.deleteSuccessLiveData.observe(viewLifecycleOwner){
            findNavController().navigate(R.id.action_detailFragment_to_ContactListFragment)
        }
      val contact = args.contact
        nameDetailTextView.text = contact.name
        val mails: StringBuffer= StringBuffer()
        contact.mails.forEach {
            mails.append(it + "\n")
        }
        Log.e("detailFragment","mail $mails")
        val phones: StringBuffer= StringBuffer()
        contact.phones.forEach {
            phones.append(it + "\n")
        }
        mailsTextView.text = mails
        phonesTextView.text = phones

deleteButton.setOnClickListener {
viewModel.deleteContact(contact)
}
    }
    private fun getAllCourses(){
        val list = context!!.contentResolver
            .query(uriProvider,null,null,null,null)
            ?.use {
                getTitleFromCursor(it)
            }.orEmpty()
        Log.d("getAllCourses", "list = $list")

    }
    private fun getTitleFromCursor(cursor: Cursor):List<Course>{
        if (cursor.moveToFirst().not()) return emptyList()
        Log.d("getTitle", "zashel")
        val list = mutableListOf<Course>()
        do {
            val titleIndex = cursor.getColumnIndex("title")
            val title = cursor.getString(titleIndex).orEmpty()
            val idIndex = cursor.getColumnIndex("id")
            val id = cursor.getLong(idIndex)
            list.add(
                Course(
                    id, title
                )
            )
        } while (cursor.moveToNext())
        Log.d("getTitle", "list = $list")
        return list
    }
}