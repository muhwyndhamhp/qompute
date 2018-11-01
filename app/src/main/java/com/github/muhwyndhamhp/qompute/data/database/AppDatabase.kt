package com.github.muhwyndhamhp.qompute.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.github.muhwyndhamhp.qompute.data.model.Build
import com.github.muhwyndhamhp.qompute.data.model.BuildDao
import com.github.muhwyndhamhp.qompute.data.model.Component
import com.github.muhwyndhamhp.qompute.data.model.ComponentDao
import com.github.muhwyndhamhp.qompute.utils.DATABASE_NAME
import com.github.muhwyndhamhp.qompute.utils.ObjectTypeConverter

@Database(entities = [Component::class, Build::class], version = 2, exportSchema = false)
@TypeConverters(ObjectTypeConverter::class)
abstract class AppDatabase : RoomDatabase(){

    abstract fun componentDao() : ComponentDao
    abstract fun buildDao() : BuildDao

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