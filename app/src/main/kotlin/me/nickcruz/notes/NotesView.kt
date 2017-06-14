package me.nickcruz.notes

import android.arch.lifecycle.LifecycleOwner

/**
 * Created by Nick Cruz on 6/10/17
 */
interface NotesView : LifecycleOwner {
    /**
     * Show the notes.
     * @param notes The notes to show. Implementations can assume this might be a completely fresh
     *              list on every call.
     */
    fun showNotes(notes: List<Note>)
}