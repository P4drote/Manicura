package com.example.manicura.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TablaCliente::class, TablaServicio::class], version = 1, exportSchema = false)
abstract class ManicuraDataBase : RoomDatabase() {

    abstract val manicuraDAO: ManicuraDAO

    companion object {

        @Volatile
        private var INSTANCE: ManicuraDataBase? = null

        fun getInstance(context: Context): ManicuraDataBase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ManicuraDataBase::class.java,
                        "manicura_database"
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}