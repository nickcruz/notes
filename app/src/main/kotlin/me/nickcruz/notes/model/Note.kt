package me.nickcruz.notes.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Nick Cruz on 6/10/17
 */
@Entity
data class Note(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name=ID) var id: Long = 0,
        @ColumnInfo(name=TITLE) var title: String = "",
        @ColumnInfo(name=CONTENT) var content: String = "")
    : Parcelable {

    constructor(title: String, content: String) : this(0, title, content)

    companion object {
        const val TABLE = "note"
        const val ID = "id"
        const val TITLE = "title"
        const val CONTENT= "constant"

        @JvmField val CREATOR: Parcelable.Creator<Note> = object : Parcelable.Creator<Note> {
            override fun createFromParcel(source: Parcel): Note = Note(source)
            override fun newArray(size: Int): Array<Note?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readLong(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(id)
        dest.writeString(title)
        dest.writeString(content)
    }
}