package me.nickcruz.notes.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import me.nickcruz.notes.model.Note

/**
 * Created by Nick Cruz on 6/10/17.
 *
 * Responsible for retrieving notes from remote or local data sources or storing/updating newly
 * created Notes.
 */
object NoteRepository {

    // TODO: Move this into a persistent DB.
    private val notes: MutableList<Note> = mutableListOf(
            Note("First Note","This is the content of the first note."),
            Note("Second Note", "This is the content of the second note."))

    private val notesLive: MutableLiveData<List<Note>> = MutableLiveData<List<Note>>()
            .apply { value = notes }

    /**
     * Get the notes.
     */
    fun getNotes(): LiveData<List<Note>> = notesLive

    /**
     * Add a new note.
     * @param note The newly created Note.
     */
    fun addNote(note: Note) {
        notes.add(note)
        notesLive.value = notes
    }
}