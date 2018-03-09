package me.nickcruz.notes.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import kotlin.reflect.KClass

/**
 * Created by nick.cruz on 3/9/18
 */
fun <T : ViewModel> AppCompatActivity.lazyViewModel(clazz: KClass<T>) = lazy {
    ViewModelProviders.of(this).get(clazz.java)
}