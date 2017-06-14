package me.nickcruz.notes.view.notes

import android.arch.lifecycle.LifecycleOwner
import me.nickcruz.notes.model.Note

/**
 * Created by Nick Cruz on 6/10/17
 */
interface NotesView : LifecycleOwner {
    /**
     * Show a list of notes.
     * Implementations can assume this might be a completely fresh list on every call.
     *
     * @param notes A List of notes.
     */
    fun showNotes(notes: List<Note>)
}