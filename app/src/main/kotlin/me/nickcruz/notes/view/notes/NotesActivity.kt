package me.nickcruz.notes.view.notes

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import butterknife.ButterKnife
import butterknife.OnClick
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_notes.*
import kotlinx.android.synthetic.main.content_notes.*
import me.nickcruz.notes.R
import me.nickcruz.notes.model.Note
import me.nickcruz.notes.view.note.NoteActivity
import me.nickcruz.notes.view.attachToLifecycle
import me.nickcruz.notes.viewmodel.notes.NotesViewModel

class NotesActivity : LifecycleActivity(), NotesAdapter.NotesAdapterListener {

    val notesAdapter = NotesAdapter(this, this)

    lateinit var notesViewModel: NotesViewModel

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        setActionBar(toolbar)
        ButterKnife.bind(this)

        notesRecyclerView.adapter = notesAdapter
        notesRecyclerView.layoutManager = LinearLayoutManager(this)

        notesViewModel = ViewModelProviders.of(this)
                .get(NotesViewModel::class.java)

        notesViewModel.notes
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ notesAdapter.setNotes(it) })
                .attachToLifecycle(this)
    }

    override fun onNoteClicked(note: Note) {
        startActivity(NoteActivity.getStartIntent(this, note))
    }

    @OnClick(R.id.fab)
    internal fun fabClicked() {
        startActivity(NoteActivity.getStartIntent(this))
    }
}
