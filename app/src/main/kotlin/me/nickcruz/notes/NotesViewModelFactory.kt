package me.nickcruz.notes

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

/**
 * Created by Nick Cruz on 6/10/17
 *
 * TODO 6/10/17: This is a horrible way to inject dependencies into the View Models. Use Dagger.
 */
class NotesViewModelFactory(val lifecycle: Lifecycle) : ViewModelProvider.Factory {

    var notesView: NotesView? = null

    fun with(notesView: NotesView): NotesViewModelFactory {
        this.notesView = notesView
        return this
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        if (modelClass?.canonicalName == "NotesViewModel" && notesView != null) {
            return NotesViewModel(lifecycle, notesView as NotesView) as T
        }
        throw RuntimeException("Unknown class: $modelClass")
    }
}