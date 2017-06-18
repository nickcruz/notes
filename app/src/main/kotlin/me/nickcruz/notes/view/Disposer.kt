package me.nickcruz.notes.view

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Nick Cruz on 6/18/17
 *
 * Disposes of any Disposables left behind from any Rx streams.
 *
 * To ensure an Rx stream is not leaked, call Disposable.attachToLifecycle on it.
 */
object Disposer : LifecycleObserver {

    val compositeDisposable = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun cleanUp() {
        compositeDisposable.clear()
    }

}

/**
 * Attaches a Disposable to a LifeCycleOwner. This attaches the Disposable to a lifecycle-aware
 * CompositeDisposable.
 */
fun Disposable.attachToLifecycle(lifecycleOwner: LifecycleOwner) {
    lifecycleOwner.lifecycle.addObserver(Disposer)
    Disposer.compositeDisposable.add(this)
}