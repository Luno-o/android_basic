package com.example.contentprovider.custom_content_provider

import android.content.*
import android.database.Cursor
import android.net.Uri
import com.example.contentprovider.BuildConfig
import com.squareup.moshi.Moshi

class SkillboxContentProvider: ContentProvider() {
    private lateinit var userPrefs: SharedPreferences
    private lateinit var coursesPrefs: SharedPreferences
private val userAdapter = Moshi.Builder()
    .build().adapter(User::class.java)
    private val courseAdapter = Moshi.Builder()
        .build().adapter(Course::class.java)
private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
    addURI(AUTHORITIES, PATH_USERS, TYPE_USERS)
    addURI(AUTHORITIES, PATH_COURSES, TYPE_COURSES)
    addURI(AUTHORITIES, "$PATH_USERS/#", TYPE_USER_ID)
    addURI(AUTHORITIES, "$PATH_COURSES/#", TYPE_COURSE_ID)

}

    override fun onCreate(): Boolean {
        userPrefs = context!!.getSharedPreferences("user_shared_prefs", Context.MODE_PRIVATE)
        coursesPrefs = context!!.getSharedPreferences("course_shared_prefs", Context.MODE_PRIVATE)
        return true
    }

    override fun query(
        uri: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
return when(uriMatcher.match(uri)){
    TYPE_USERS->{}

    else->null
}
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }
    companion object{
        private const val AUTHORITIES = "${BuildConfig.APPLICATION_ID}.provider"
        private const val PATH_USERS = "users"
        private const val PATH_COURSES = "courses"

        private const val TYPE_USERS = 1
        private const val TYPE_COURSES = 2
        private const val TYPE_USER_ID = 3
        private const val TYPE_COURSE_ID = 4
    }
}