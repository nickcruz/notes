package me.nickcruz.notes.view

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by nick.cruz on 3/9/18
 */
class Disposer(lifecycleOwner: LifecycleOwner) : LifecycleObserver {

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    private val compositeDisposable = CompositeDisposable()

    fun dispose(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun clear() {
        compositeDisposable.clear()
    }
}