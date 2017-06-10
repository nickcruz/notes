package me.nickcruz.notes

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

/**
 * Created by Nick Cruz on 6/10/17.
 *
 * Intermediary between ViewModel layer and Data Source layer.
 *
 * Retrieves models from Data Sources for ViewModels.
 */
object NoteRepository {

    val notes: List<Note> = listOf(Note("First Note"), Note("Second Note"))

    fun getNotes(): LiveData<List<Note>> {
        val liveData = MutableLiveData<List<Note>>()
        liveData.value = notes
        return liveData
    }
}