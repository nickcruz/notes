package me.nickcruz.notes.viewmodel.note

import android.arch.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import me.nickcruz.notes.model.Note
import me.nickcruz.notes.repository.NoteRepository

/**
 * Created by Nick Cruz on 6/18/17
 */
class NoteViewModel : ViewModel() {
    var note = Note()

    fun subscribeToTitleChanges(titleChanges: Observable<CharSequence>): Completable = titleChanges
            .doOnNext { note.title = it.toString() }
            .ignoreElements()

    fun subscribeToContentChanges(contentChanges: Observable<CharSequence>): Completable = contentChanges
            .doOnNext { note.content = it.toString() }
            .ignoreElements()

    fun submitNote() = NoteRepository.replaceNote(note)

}