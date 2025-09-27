package com.example.spirala1

import com.google.gson.annotations.SerializedName

data class Pretraga(
    @SerializedName("data")
    val data: BiljkaSingleResponse,

    @SerializedName("links")
    val links: Links?
)
