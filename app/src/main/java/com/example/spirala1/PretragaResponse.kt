package com.example.spirala1


import com.google.gson.annotations.SerializedName

data class PretragaResponse(
    @SerializedName("data")
    val data: List<BiljkaResponse>?,

    @SerializedName("links")
    val links: Links?,

    @SerializedName("meta")
    val meta: Meta
)

data class Meta(
    @SerializedName("total")
    val total: Int
)