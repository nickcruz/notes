package me.nickcruz.notes.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import me.nickcruz.notes.model.Note

/**
 * Created by Nick Cruz on 6/15/17
 */
@Database(entities = arrayOf(Note::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
}