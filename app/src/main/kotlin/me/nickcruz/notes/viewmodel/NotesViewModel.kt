package me.nickcruz.notes.viewmodel

import android.arch.lifecycle.*
import me.nickcruz.notes.model.Note
import me.nickcruz.notes.repository.NoteRepository
import me.nickcruz.notes.view.notes.NotesView

/**
 * Created by Nick Cruz on 6/10/17
 */
class NotesViewModel : ViewModel() {

    val notes: LiveData<List<Note>> = NoteRepository.getNotes()

    fun subscribe(owner: NotesView): NotesViewModel {
        notes.observe(owner, Observer {
            owner.showNotes(it ?: emptyList())
        })
        return this
    }
}
