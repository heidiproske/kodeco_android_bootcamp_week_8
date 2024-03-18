package com.kodeco.android.countryinfo.models

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Country(
    val name: CountryName,
    val capital: List<String>?,
    val population: Long,
    val area: Float,
    val flags: CountryFlags,
) : Parcelable {
    @IgnoredOnParcel
    val mainCapital = capital?.firstOrNull() ?: "N/A"
    @IgnoredOnParcel
    val commonName = name.common
    @IgnoredOnParcel
    val flagUrl = flags.png
}
