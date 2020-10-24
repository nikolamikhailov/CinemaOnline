package ru.l4gunner4l.cinemaonline.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenreRemoteModel(
    @SerializedName("name")
    val name: String
) : Parcelable