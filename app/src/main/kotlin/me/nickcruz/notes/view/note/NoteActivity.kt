package me.nickcruz.notes.view.note

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import butterknife.ButterKnife
import butterknife.OnClick
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_note.*
import kotlinx.android.synthetic.main.content_note.*
import me.nickcruz.notes.R
import me.nickcruz.notes.model.Note
import me.nickcruz.notes.view.attachToLifecycle
import me.nickcruz.notes.view.camera.CameraActivity
import me.nickcruz.notes.viewmodel.note.NoteViewModel

/**
 * Created by Nick Cruz on 6/13/17
 */
class NoteActivity : LifecycleActivity() {

    companion object {
        val EXTRA_NOTE = "note"

        fun getStartIntent(context: Context, note: Note? = null) =
                Intent(context, NoteActivity::class.java).apply { putExtra(EXTRA_NOTE, note) }
    }

    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        setActionBar(toolbar)
        ButterKnife.bind(this)

        noteViewModel = ViewModelProviders.of(this)
                .get(NoteViewModel::class.java)

        intent.getParcelableExtra<Note>(EXTRA_NOTE)?.let {
            noteViewModel.note = it
            titleEditText.setText(it.title)
            contentEditText.setText(it.content)
        }

        noteViewModel
                .subscribeToChanges(titleEditText.textChanges(), contentEditText.textChanges())
                .subscribe()
                .attachToLifecycle(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_camera -> {
                goToCamera()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    @OnClick(R.id.fab)
    internal fun submitClicked() {
        noteViewModel
                .submitNote()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe { finish() }
                .attachToLifecycle(this)
    }

    private fun goToCamera() =
            startActivity(CameraActivity.getStartIntent(this, noteViewModel.note))
}