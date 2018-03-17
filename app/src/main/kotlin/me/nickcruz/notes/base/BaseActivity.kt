package me.nickcruz.notes.base

import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.Disposable
import me.nickcruz.notes.view.Disposer

/**
 * Created by nick.cruz on 3/16/18
 */
abstract class BaseActivity : AppCompatActivity() {

    private val disposer by lazy { Disposer(this) }

    protected fun Disposable.addToDisposer() {
        disposer.dispose(this)
    }

}