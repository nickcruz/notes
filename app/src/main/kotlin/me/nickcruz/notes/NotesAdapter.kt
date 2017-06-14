package me.nickcruz.notes

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView

/**
 * Created by Nick Cruz on 6/10/17
 */
class NotesAdapter(val context: Context) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private var notes: List<Note> = emptyList()

    fun setNotes(notes: List<Note>) {
        this.notes = notes
    }

    override fun onBindViewHolder(holder: NoteViewHolder?, position: Int) {
        holder?.noteContent?.text = notes[position].content
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NoteViewHolder {
        return NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note, parent, false))
    }

    override fun getItemCount(): Int = notes.size

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteContent: TextView by bindView(R.id.noteContentTextView)
    }
}