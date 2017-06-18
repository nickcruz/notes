package me.nickcruz.notes.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import me.nickcruz.notes.model.Note

/**
 * Created by Nick Cruz on 6/15/17
 */
@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getNotes(): Flowable<List<Note>>

    @Insert
    fun insert(note: Note)
}