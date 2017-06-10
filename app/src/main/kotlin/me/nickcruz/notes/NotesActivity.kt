package me.nickcruz.notes

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import kotlinx.android.synthetic.main.activity_notes.*

class NotesActivity : LifecycleActivity(), NotesView {

    lateinit var viewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        setActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        // Initialize viewModel
        viewModel = ViewModelProviders.of(this, NotesViewModelFactory(this.lifecycle).with(this))
                .get(NotesViewModel::class.java)
    }

    override fun showNotes(notes: List<Note>) {
        // Set new notes on the adapter
    }

}
