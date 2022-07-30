package com.example.testapplication

import android.content.ContentUris
import android.content.ContentValues
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapplication.databinding.FragmentFirstBinding
import kotlin.random.Random


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private var courseAdapter: CourseAdapter? = null
    private val binding get() = _binding!!
    private var courseList = arrayListOf(
        "Разработчик андроид",
        "Разработчик андрей",
        "Разработчик налей",
        "Разработчик полей",
        "Разработчик сетей"

    )
    private var idCourse = 0L
    private val providerAuthority = "com.example.contentprovider.provider"
    private val uriProviderCourses = "com.example.contentprovider.provider/courses"
    private val uriProvider = Uri.parse("content://$uriProviderCourses")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()

        binding.addButton.setOnClickListener {
            if (contentProviderCheck(providerAuthority)) {

                addCourse(courseList[Random.nextInt(0, courseList.size - 1)])
            } else {
                Toast.makeText(context, "Требуемый провайдер отсутвует", Toast.LENGTH_SHORT).show()
            }

        }
        binding.deleteAllButton.setOnClickListener {
            if (contentProviderCheck(providerAuthority)) {

                deleteAllCourses()
            } else {
                Toast.makeText(context, "Требуемый провайдер отсутвует", Toast.LENGTH_SHORT).show()
            }
        }
        binding.deleteButton.setOnClickListener {
            if (contentProviderCheck(providerAuthority)) {

                deleteCourse()
            } else {
                Toast.makeText(context, "Требуемый провайдер отсутвует", Toast.LENGTH_SHORT).show()
            }

        }
        binding.updateCourse.setOnClickListener {
            if (contentProviderCheck(providerAuthority)) {

                updateLastCourse()
            } else {
                Toast.makeText(context, "Требуемый провайдер отсутвует", Toast.LENGTH_SHORT).show()
            }
        }
        binding.getCoursesButton.setOnClickListener {
            if (contentProviderCheck(providerAuthority)) {

                getAllCourses()
            } else {
                Toast.makeText(context, "Требуемый провайдер отсутвует", Toast.LENGTH_SHORT).show()
            }

        }


    }

    private fun initList() {
        courseAdapter = CourseAdapter()
        with(binding.recyclerViewCourse) {
            adapter = courseAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun getAllCourses() {
        val list = context!!.contentResolver
            .query(uriProvider, null, null, null, null)
            ?.use {
                getTitleFromCursor(it)
            }.orEmpty()
        Log.d("getAllCourses", "list = $list")
        courseAdapter?.items = list
    }

    private fun contentProviderCheck(authority: String): Boolean {
        var check: Boolean = false
        for (pack in context!!.packageManager.getInstalledPackages(PackageManager.GET_PROVIDERS)) {
            val providers = pack.providers
            if (providers != null) {
                for (provider in providers) {

                    if (provider.authority == authority) {
                        check = true
                        Log.d("ContentProviderCheck", "provider: " + provider.authority)
                    }
                }
            }
        }
        Log.d("ContentProviderCheck", "provider: $check")
        return check
    }

    private fun getTitleFromCursor(cursor: Cursor): List<Course> {
        Log.d("getTitle", "${cursor.count}")
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

    private fun updateLastCourse() {
        val idCourse = courseAdapter?.items?.last()?.id
        Log.d("update", "id = $idCourse")
        val courseTitle = courseList[Random.nextInt(0, courseList.size - 1)]
        val contentValues = ContentValues().apply {
            put("id", idCourse)
            put("title", courseTitle)
        }
        Log.d("update", "title = $courseTitle")
        if (idCourse != null) {
            val uriId = ContentUris.withAppendedId(
                uriProvider, idCourse
            )
            context!!.contentResolver.update(uriId, contentValues, null, null)
        }

        getAllCourses()
    }

    private fun deleteAllCourses() {

        context!!.contentResolver.delete(uriProvider, null, null)
        getAllCourses()
    }

    private fun addCourse(title: String) {
        val courseId = idCourse++
        saveCourseTitle(courseId, title)
        getAllCourses()
    }

    private fun saveCourseTitle(courseId: Long, title: String) {
        val contentValues = ContentValues().apply {
            put("id", courseId)
            put("title", title)
        }
        val uriResult = context!!.contentResolver.insert(uriProvider, contentValues)
        Log.d("saveCourse", "uri = $uriResult")
    }

    private fun deleteCourse(): Int {
        val idCourse = courseAdapter?.items?.last()?.id
        return if (idCourse != null) {
            val uriId = ContentUris.withAppendedId(
                uriProvider, idCourse
            )
            val count = context!!.contentResolver.delete(uriId, null, null)
            getAllCourses()
            count
        } else 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}