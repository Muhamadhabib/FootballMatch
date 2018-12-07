package com.example.habib.teamfav.model

import android.support.annotation.StringRes
import com.google.gson.annotations.SerializedName

data class Teams(
        @SerializedName("idTeam")
        var teamId:String? = "",
        @SerializedName("strTeam")
        var teamName:String? = "",
        @SerializedName("strTeamBadge")
        var teamBadge:String? = "",
        @SerializedName("intFormedYear")
        var teamFormedYear:String? = "",
        @SerializedName("strStadium")
        var teamStadium:String? = "",
        @SerializedName("strDescriptionEN")
        var teamDescription:String? = ""
)