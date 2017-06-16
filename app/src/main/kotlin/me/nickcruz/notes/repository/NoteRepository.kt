package me.nickcruz.notes.repository

import android.arch.lifecycle.LiveData
import android.os.AsyncTask
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
    fun getNotes(): LiveData<List<Note>> = App.database.getNoteDao().getNotes()

    /**
     * Add a new note.
     *
     * TODO: Maybe don't use an AsyncTask.
     *
     * @param note The newly created Note.
     */
    fun addNote(note: Note) = with (App.database) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                beginTransaction()
                try {
                    getNoteDao().insert(note)
                    setTransactionSuccessful()
                } finally {
                    endTransaction()
                }
                return null
            }
        }.execute()
    }
}