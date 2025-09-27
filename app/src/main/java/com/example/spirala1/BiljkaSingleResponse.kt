package com.example.spirala1

import com.google.gson.annotations.SerializedName

data class BiljkaSingleResponse(

    @SerializedName("id")
    val id: Int,

    @SerializedName("slug")
    val slug: String,

    @SerializedName("common_name")
    val commonName: String,

    @SerializedName("scientific_name")
    val scientificName: String,

    @SerializedName("family")
    val family: Porodica,

    @SerializedName("image_url")
    val imageUrl: String?,

    @SerializedName("main_species")
    val mainSpecies: Vrsta?,

    @SerializedName("links")
    val links: Links?
)

data class Porodica(
    @SerializedName("name")
    val name: String
)
