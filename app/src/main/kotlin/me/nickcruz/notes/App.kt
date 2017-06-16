package me.nickcruz.notes

import android.app.Application
import android.arch.persistence.room.Room
import me.nickcruz.notes.db.AppDatabase

/**
 * Created by Nick Cruz on 6/15/17
 */
class App : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AppDatabase::class.java, "notes-db").build()
    }
}