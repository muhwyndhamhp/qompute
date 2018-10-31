package com.example.muhwyndham.qompute.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.muhwyndham.qompute.data.model.Component
import com.example.muhwyndham.qompute.data.model.ComponentDao
import com.example.muhwyndham.qompute.utils.DATABASE_NAME

@Database(entities = [Component::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun componentDao() : ComponentDao

    companion object {

        @Volatile private var instance : AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also{instance = it}
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {

            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .addCallback(object: RoomDatabase.Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                    }
                })
                .build()
        }

    }
}