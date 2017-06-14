package me.nickcruz.notes

import android.arch.lifecycle.*

/**
 * Created by Nick Cruz on 6/10/17
 */
class NotesViewModel : ViewModel() {

    val notes: LiveData<List<Note>> = NoteRepository.getNotes()

}
