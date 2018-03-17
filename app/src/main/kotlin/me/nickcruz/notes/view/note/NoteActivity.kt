package me.nickcruz.notes.view.note

import android.content.Context
import android.content.Intent
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_note.*
import kotlinx.android.synthetic.main.content_note.*
import me.nickcruz.notes.R
import me.nickcruz.notes.base.BaseActivity
import me.nickcruz.notes.model.Note
import me.nickcruz.notes.viewmodel.lazyViewModel
import me.nickcruz.notes.viewmodel.note.NoteViewModel

/**
 * Created by Nick Cruz on 6/13/17
 */
class NoteActivity : BaseActivity() {

    companion object {
        const val EXTRA_NOTE = "note"

        fun getStartIntent(context: Context, note: Note? = null) =
                Intent(context, NoteActivity::class.java).apply { putExtra(EXTRA_NOTE, note) }
    }

    private val noteViewModel by lazyViewModel(NoteViewModel::class)

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        setActionBar(toolbar)

        intent.getParcelableExtra<Note>(EXTRA_NOTE)?.let {
            noteViewModel.note = it
            titleEditText.setText(it.title)
            contentEditText.setText(it.content)
        }

        fab.setOnClickListener {
            noteViewModel
                    .submitNote()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::finish)
                    .addToDisposer()
        }

        noteViewModel
                .subscribeToTitleChanges(titleEditText.textChanges())
                .subscribeOn(Schedulers.io())
                .subscribe()
                .addToDisposer()

        noteViewModel
                .subscribeToContentChanges(contentEditText.textChanges())
                .subscribeOn(Schedulers.io())
                .subscribe()
                .addToDisposer()
    }
}