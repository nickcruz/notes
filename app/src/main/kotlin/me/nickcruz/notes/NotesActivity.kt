package me.nickcruz.notes

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_notes.*
import kotlinx.android.synthetic.main.content_notes.*

class NotesActivity : LifecycleActivity(), NotesView {

    lateinit var viewModel: NotesViewModel

    val notesAdapter = NotesAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        setActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        notesRecyclerView.adapter = notesAdapter
        notesRecyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize viewModel
        viewModel = ViewModelProviders.of(this, NotesViewModelFactory(this.lifecycle).with(this))
                .get(NotesViewModel::class.java)
    }

    override fun showNotes(notes: List<Note>) {
        notesAdapter.setNotes(notes)
    }

}