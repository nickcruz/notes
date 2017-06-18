package me.nickcruz.notes.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import me.nickcruz.notes.model.Note
import me.nickcruz.notes.model.Note.Companion.ID
import me.nickcruz.notes.model.Note.Companion.TABLE

/**
 * Created by Nick Cruz on 6/15/17
 */
@Dao
interface NoteDao {
    @Query("SELECT * FROM $TABLE")
    fun getNotes(): Flowable<List<Note>>

    @Insert
    fun insert(note: Note)

    @Delete
    fun delete(note: Note)
}