package me.nickcruz.notes.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import me.nickcruz.notes.App
import me.nickcruz.notes.model.Note

/**
 * Created by Nick Cruz on 6/10/17.
 *
 * Responsible for retrieving notes from remote or local data sources or storing/updating newly
 * created Notes.
 */
object NoteRepository {

    private val noteDao = App.database.noteDao

    /**
     * Get the notes.
     */
    fun getNotes(): Flowable<List<Note>> = noteDao.getNotes()

    /**
     * Add a new note.
     *
     * @param note The newly created Note.
     */
    fun insertNote(note: Note): Completable = Completable.fromAction { noteDao.insert(note) }

    /**
     * Update a note.
     *
     * @param note The note to update.
     */
    fun updateNote(note: Note): Completable = Completable.fromAction { noteDao.update(note) }
}