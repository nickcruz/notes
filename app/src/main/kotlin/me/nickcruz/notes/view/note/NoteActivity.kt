package me.nickcruz.notes.view.note

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_note.*
import kotlinx.android.synthetic.main.content_note.*
import me.nickcruz.notes.R
import me.nickcruz.notes.model.Note
import me.nickcruz.notes.view.attachToLifecycle
import me.nickcruz.notes.viewmodel.lazyViewModel
import me.nickcruz.notes.viewmodel.note.NoteViewModel

/**
 * Created by Nick Cruz on 6/13/17
 */
class NoteActivity : AppCompatActivity() {

    companion object {
        val EXTRA_NOTE = "note"

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

        fab.setOnClickListener { noteViewModel
                .submitNote()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::finish)
                .attachToLifecycle(this)
        }

        noteViewModel
                .subscribeToTitleChanges(titleEditText.textChanges())
                .subscribe()
                .attachToLifecycle(this)

        noteViewModel
                .subscribeToContentChanges(contentEditText.textChanges())
                .subscribe()
                .attachToLifecycle(this)
    }
}