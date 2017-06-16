package me.nickcruz.notes.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Nick Cruz on 6/10/17
 */
@Entity(tableName = "note")
data class Note(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,
        var title: String = "",
        var content: String = "") {

    constructor(title: String, content: String) : this(0, title, content)
}