package me.nickcruz.notes.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import me.nickcruz.notes.model.Note

/**
 * Created by Nick Cruz on 6/15/17
 */
@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getNotes(): LiveData<List<Note>>

    @Insert
    fun insert(note: Note)
}