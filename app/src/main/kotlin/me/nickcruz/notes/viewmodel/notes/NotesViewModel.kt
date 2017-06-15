package me.nickcruz.notes.viewmodel.notes

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import me.nickcruz.notes.model.Note
import me.nickcruz.notes.repository.NoteRepository

/**
 * Created by Nick Cruz on 6/10/17
 */
class NotesViewModel : ViewModel() {

    val notes: LiveData<List<Note>> = NoteRepository.getNotes()

    fun addNote(title: String, content: String) {
        NoteRepository.addNote(Note(title, content))
    }
}
