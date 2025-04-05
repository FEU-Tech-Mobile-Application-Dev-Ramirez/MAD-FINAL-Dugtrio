package com.example.mad_collaborative.utils

import android.os.Parcel
import android.os.Parcelable

data class Challenge(
    val name: String,
    val description: String,
    val category: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(category)
    }

    override fun describeContents(): Int = 0

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Challenge> = object : Parcelable.Creator<Challenge> {
            override fun createFromParcel(parcel: Parcel): Challenge {
                return Challenge(parcel)
            }

            override fun newArray(size: Int): Array<Challenge?> {
                return arrayOfNulls(size)
            }
        }
    }
}
