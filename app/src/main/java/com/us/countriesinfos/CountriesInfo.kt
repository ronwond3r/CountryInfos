package com.us.countriesinfos

import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryInfo(
    @SerializedName("name") val name: Name,
    @SerializedName("currencies") val currencies: Map<String, Currency>,
    @SerializedName("capital") val capital: List<String>,
    @SerializedName("region") val region: String,
    @SerializedName("languages") val languages: Map<String, String>,
    @SerializedName("translations") val translations: Map<String, Translation>,
    @SerializedName("borders") val borders: List<String>,
    @SerializedName("area") val area: Int,
    @SerializedName("flag") val flag: String,
    @SerializedName("flags") val flags: Flags,
    @SerializedName("population") val population: Long
) : Parcelable {
    override fun describeContents(): Int {
        return 0
    }
}



@Parcelize
data class Name(
    @SerializedName("common") val common: String,
    @SerializedName("official") val official: String,
    @SerializedName("nativeName") val nativeName: NativeName
) : Parcelable

@Parcelize
data class NativeName(
    @SerializedName("eng") val eng: LanguageNames,
    @SerializedName("swa") val swa: LanguageNames
) : Parcelable

@Parcelize
data class LanguageNames(
    @SerializedName("official") val official: String,
    @SerializedName("common") val common: String
) : Parcelable


@Parcelize
data class Eng(
    @SerializedName("official") val official: String,
    @SerializedName("common") val common: String
) : Parcelable

@Parcelize
data class Swa(
    @SerializedName("official") val official: String,
    @SerializedName("common") val common: String
) : Parcelable

@Parcelize
data class Currency(
    @SerializedName("name") val name: String,
    @SerializedName("symbol") val symbol: String
) : Parcelable

@Parcelize
data class Idd(
    @SerializedName("root") val root: String,
    @SerializedName("suffixes") val suffixes: List<String>
) : Parcelable

@Parcelize
data class Translation(
    @SerializedName("ara") val ara: Ara,
    @SerializedName("bre") val bre: Bre,
    // ... Add other translations as needed
) : Parcelable

@Parcelize
data class Ara(
    @SerializedName("official") val official: String,
    @SerializedName("common") val common: String
) : Parcelable

@Parcelize
data class Bre(
    @SerializedName("official") val official: String,
    @SerializedName("common") val common: String
) : Parcelable

// ... Add other translation classes as needed

@Parcelize
data class Demonym(
    @SerializedName("eng") val eng: Eng,
    @SerializedName("fra") val fra: Fra
) : Parcelable


@Parcelize
data class Fra(
    @SerializedName("f") val f: String,
    @SerializedName("m") val m: String
) : Parcelable

@Parcelize
data class Maps(
    @SerializedName("googleMaps") val googleMaps: String,
    @SerializedName("openStreetMaps") val openStreetMaps: String
) : Parcelable

@Parcelize
data class Flags(
    @SerializedName("png") val png: String,
    @SerializedName("svg") val svg: String,
    @SerializedName("alt") val alt: String
) : Parcelable

@Parcelize
data class CoatOfArms(
    @SerializedName("png") val png: String,
    @SerializedName("svg") val svg: String
) : Parcelable

@Parcelize
data class CapitalInfo(
    @SerializedName("latlng") val latlng: List<Double>
) : Parcelable

@Parcelize
data class PostalCode(
    @SerializedName("format") val format: String,
    @SerializedName("regex") val regex: String
) : Parcelable

@Parcelize
data class Car(
    @SerializedName("signs") val signs: List<String>,
    @SerializedName("side") val side: String
) : Parcelable

