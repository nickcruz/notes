package me.nickcruz.notes.view.notes

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import butterknife.ButterKnife
import butterknife.OnClick
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_notes.*
import kotlinx.android.synthetic.main.content_notes.*
import me.nickcruz.notes.R
import me.nickcruz.notes.view.add.AddNoteActivity
import me.nickcruz.notes.viewmodel.notes.NotesViewModel

class NotesActivity : LifecycleActivity() {

    val notesAdapter = NotesAdapter(this)
    val compositeDisposable = CompositeDisposable()

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

        compositeDisposable.add(notesViewModel.notes
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ notesAdapter.setNotes(it) }))
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    @OnClick(R.id.fab)
    internal fun fabClicked() {
        startActivity(AddNoteActivity.getStartIntent(this))
    }
}
