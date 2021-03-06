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
    val notes: Flowable<List<Note>>
        get() = noteDao.getNotes()

    /**
     * Replaces a note. If the note does not exist, inserts the note.
     *
     * @param note The newly created Note.
     */
    fun replaceNote(note: Note): Completable = Completable.mergeArray(
            Completable.fromAction { noteDao.replace(note) },
            Completable.fromAction {
//                FirebaseFirestore.getInstance()
//                        .collection("notes")
//                        .document("${note.id}")
//                        .set(note)
            }
    )
}