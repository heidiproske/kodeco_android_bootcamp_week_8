package com.kodeco.android.countryinfo.models

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class CountryName(val common: String) : Parcelable
