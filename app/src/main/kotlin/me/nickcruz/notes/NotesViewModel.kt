package me.nickcruz.notes

import android.arch.lifecycle.*

/**
 * Created by Nick Cruz on 6/10/17
 */
class NotesViewModel : ViewModel() {

    val notes: LiveData<List<Note>> = NoteRepository.getNotes()

    fun subscribe(owner: NotesView) {
        notes.observe(owner, Observer {
            owner.showNotes(it ?: emptyList())
        })
    }
}
