package me.nickcruz.notes.view.notes

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_notes.*
import kotlinx.android.synthetic.main.content_notes.*
import me.nickcruz.notes.R

class NotesActivity : LifecycleActivity(), NotesView {

    val notesAdapter = NotesAdapter(this)

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(me.nickcruz.notes.R.layout.activity_notes)
        setActionBar(toolbar)
        butterknife.ButterKnife.bind(this)

        notesRecyclerView.adapter = notesAdapter
        notesRecyclerView.layoutManager = android.support.v7.widget.LinearLayoutManager(this)

        ViewModelProviders.of(this)
                .get(me.nickcruz.notes.viewmodel.NotesViewModel::class.java)
                .subscribe(this)
    }

    override fun showNotes(notes: List<me.nickcruz.notes.model.Note>) {
        notesAdapter.setNotes(notes)
    }

    @butterknife.OnClick(R.id.fab)
    internal fun fabClicked() {

    }
}
