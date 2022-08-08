package com.example.roomdao.data.db

import android.content.Context
import androidx.room.Room

object Database {


    lateinit var instance: SkillBoxDatabase
    private set
    fun init(context: Context){
     instance = Room.databaseBuilder(context,
        SkillBoxDatabase::class.java,
     SkillBoxDatabase.DB_NAME)
         .addMigrations(MIGRATIONS_1_2)
         .build()
    }
}
//val MIGRATION_1_2 = object : Migration(1, 2) {
//    override fun migrate(database: SupportSQLiteDatabase) {
//        database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, `name` TEXT, " +
//                "PRIMARY KEY(`id`))")
//    }
//}
//
//val MIGRATION_2_3 = object : Migration(2, 3) {
//    override fun migrate(database: SupportSQLiteDatabase) {
//        database.execSQL("ALTER TABLE Book ADD COLUMN pub_year INTEGER")
//    }
//}
//
//Room.databaseBuilder(applicationContext, MyDb::class.java, "database-name")
//.addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()