package me.nickcruz.notes.view.notes

import android.support.v7.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_notes.*
import kotlinx.android.synthetic.main.content_notes.*
import me.nickcruz.notes.R
import me.nickcruz.notes.base.BaseActivity
import me.nickcruz.notes.view.note.NoteActivity
import me.nickcruz.notes.viewmodel.lazyViewModel
import me.nickcruz.notes.viewmodel.notes.NotesViewModel

class NotesActivity : BaseActivity() {

    private val notesViewModel by lazyViewModel(NotesViewModel::class)

    private val notesAdapter = NotesAdapter(this)

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        setActionBar(toolbar)

        notesAdapter.listener = { startActivity(NoteActivity.getStartIntent(this, it)) }
        notesRecyclerView.adapter = notesAdapter
        notesRecyclerView.layoutManager = LinearLayoutManager(this)

        notesViewModel.notes
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { notesAdapter.setNotes(it) }
                .addToDisposer()

        fab.setOnClickListener { startActivity(NoteActivity.getStartIntent(this)) }
    }
}
