package com.example.habib.teamfav.model

import com.google.gson.annotations.SerializedName

data class Player(
        @SerializedName("idPlayer")
        var idPlayer:String? = "",
        @SerializedName("strPlayer")
        var strPlayer:String? = "",
        @SerializedName("strDescriptionEN")
        var strDescriptionEN:String? = "",
        @SerializedName("strPosition")
        var strPosition:String? = "",
        @SerializedName("strHeight")
        var strHeight:String? = "",
        @SerializedName("strWeight")
        var strWeight:String? = "",
        @SerializedName("strThumb")
        var strThumb:String? = "",
        @SerializedName("strCutout")
        var strCutout:String? = ""
)