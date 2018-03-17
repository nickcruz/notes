package me.nickcruz.notes.viewmodel.notes

import android.arch.lifecycle.ViewModel
import io.reactivex.Flowable
import me.nickcruz.notes.model.Note
import me.nickcruz.notes.repository.NoteRepository

/**
 * Created by Nick Cruz on 6/10/17
 */
class NotesViewModel : ViewModel() {

    val notes: Flowable<List<Note>> = NoteRepository.notes
}
