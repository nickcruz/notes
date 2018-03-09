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

    private fun getNoteDao() = App.database.noteDao

    /**
     * Get the notes.
     */
    fun getNotes(): Flowable<List<Note>> = getNoteDao().getNotes()

    /**
     * Add a new note. If this note already exists, replaces the note.
     *
     * @param note The newly created Note.
     */
    fun insertNote(note: Note): Completable = Completable
            .fromAction { getNoteDao().delete(note) }
            .andThen(Completable.fromAction { getNoteDao().insert(note) })
}