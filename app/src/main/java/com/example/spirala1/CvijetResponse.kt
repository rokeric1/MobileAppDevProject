package com.example.spirala1

import com.google.gson.annotations.SerializedName

data class CvijetResponse(
    @SerializedName("data")
    val data : List<BiljkaResponse>,

    @SerializedName("links")
    val links: Links
)
