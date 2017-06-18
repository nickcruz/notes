package me.nickcruz.notes.view.notes

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_note.view.*
import me.nickcruz.notes.R
import me.nickcruz.notes.model.Note

/**
 * Created by Nick Cruz on 6/10/17
 */
class NotesAdapter(private val context: Context,
                   private var listener: NotesAdapterListener? = null)
    : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    interface NotesAdapterListener {
        fun onNoteClicked(note: Note)
    }

    private var notes: List<Note> = emptyList()

    fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false)
        val noteViewHolder = NoteViewHolder(view)
        listener?.let { adapterListener ->
            noteViewHolder.itemView.setOnClickListener {
                adapterListener.onNoteClicked(noteViewHolder.note)
            }
        }
        return noteViewHolder
    }

    override fun onBindViewHolder(holder: NoteViewHolder?, position: Int) {
        holder?.let {
            with(notes[position]) {
                it.note = this
                it.titleText.text = title
                it.contentText.text = content
            }
        }
    }

    override fun getItemCount(): Int = notes.size

    class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var note: Note
        val titleText: TextView = itemView.noteTitleText
        val contentText: TextView = itemView.noteContentText
    }
}