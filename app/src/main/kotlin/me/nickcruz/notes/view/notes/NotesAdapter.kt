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
class NotesAdapter(private val context: Context)
    : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    var listener: (Note) -> Unit = { }

    private val notes: MutableList<Note> = mutableListOf()

    fun setNotes(notes: List<Note>) {
        this.notes.clear()
        this.notes.addAll(notes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false)
        val noteViewHolder = NoteViewHolder(view)
        noteViewHolder.itemView.setOnClickListener {
            listener.invoke(noteViewHolder.note)
        }
        return noteViewHolder
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.let {
            with(notes[position]) {
                it.note = this
                it.titleText.text = title
                it.contentText.text = content
            }
        }
    }

    override fun getItemCount() = notes.size

    class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var note: Note
        val titleText: TextView = itemView.noteTitleText
        val contentText: TextView = itemView.noteContentText
    }
}