package me.nickcruz.notes.repository

import android.os.AsyncTask
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.nickcruz.notes.App
import me.nickcruz.notes.model.Note

/**
 * Created by Nick Cruz on 6/10/17.
 *
 * Responsible for retrieving notes from remote or local data sources or storing/updating newly
 * created Notes.
 */
object NoteRepository {

    /**
     * Get the notes.
     */
    fun getNotes(): Flowable<List<Note>> = App.database.getNoteDao().getNotes()

    /**
     * Add a new note.
     *
     * @param note The newly created Note.
     */
    fun addNote(note: Note): Completable = Completable
            .fromAction { App.database.getNoteDao().insert(note) }
}