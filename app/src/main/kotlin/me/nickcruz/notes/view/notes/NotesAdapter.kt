package me.nickcruz.notes.view.notes

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView
import me.nickcruz.notes.R
import me.nickcruz.notes.model.Note

/**
 * Created by Nick Cruz on 6/10/17
 */
class NotesAdapter(val context: Context) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private var notes: List<Note> = emptyList()

    fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: NoteViewHolder?, position: Int) {
        if (holder == null) {
            return
        }

        val (title, content) = notes[position]
        with (holder) {
            noteTitleText.text = title
            noteContentText.text = content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NoteViewHolder {
        return NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note, parent, false))
    }

    override fun getItemCount(): Int = notes.size

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTitleText: TextView by bindView(R.id.noteTitleText)
        val noteContentText: TextView by bindView(R.id.noteContentText)
    }
}