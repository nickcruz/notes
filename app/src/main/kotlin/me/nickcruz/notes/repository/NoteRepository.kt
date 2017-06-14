package me.nickcruz.notes.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import me.nickcruz.notes.model.Note

/**
 * Created by Nick Cruz on 6/10/17.
 *
 * Intermediary between ViewModel layer and Data Source layer.
 *
 * Retrieves models from Data Sources for ViewModels.
 */
object NoteRepository {

    val notes: List<Note> = listOf(
            Note("First Note","This is the content of the first note."),
            Note("Second Note", "This is the content of the second note."))

    fun getNotes(): LiveData<List<Note>> {
        val liveData = MutableLiveData<List<Note>>()
        liveData.value = notes
        return liveData
    }
}