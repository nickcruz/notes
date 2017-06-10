package me.nickcruz.notes

import android.arch.lifecycle.*

/**
 * Created by Nick Cruz on 6/10/17
 */
class NotesViewModel(val lifeCycle: Lifecycle, val notesView: NotesView) : ViewModel(), LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        NoteRepository.getNotes()
                .observe({ notesView.showNotes(it ?: emptyList()) })
    }

    // TODO 6/10/17: Possibly move this into a base ViewModel class.
    fun <T> LiveData<T>.observe(onChanged: (t: T?) -> Unit) = observe({ lifeCycle }, onChanged)

}
