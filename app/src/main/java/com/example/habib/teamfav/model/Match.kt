package com.example.habib.teamfav.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Match(
        @SerializedName("idEvent")
        val idEvent:String?="",
        @SerializedName("strEvent")
        val strEvent:String?="",
        @SerializedName("strLeague")
        val strLeague:String?="",
        @SerializedName("idHomeTeam")
        val idHomeTeam:String?="",
        @SerializedName("strHomeTeam")
        val strHomeTeam:String?="",
        @SerializedName("intHomeScore")
        val intHomeScore:String?="",
        @SerializedName("strHomeGoalDetails")
        val strHomeGoalDetails:String?="",
//        @SerializedName("strHomeRedCards")
//        val strHomeRedCards:String?="",
//        @SerializedName("strHomeYellowCards")
//        val strHomeYellowCrads:String?="",
        @SerializedName("strHomeLineupGoalkeeper")
        val strHomeLineupGoalKeeper:String?="",
        @SerializedName("strHomeLineupDefense")
        val strHomeLineupDefense:String?="",
        @SerializedName("strHomeLineupMidfield")
        val strHomeLineupMidfield:String?="",
        @SerializedName("strHomeLineupForward")
        val strHomeLineupForward:String?="",
        @SerializedName("strHomeLineupSubstitutes")
        val strHomeLineupSubstitutes:String?="",
        @SerializedName("idAwayTeam")
        val idAwayTeam:String?="",
        @SerializedName("strAwayTeam")
        val strAwayTeam:String?="",
        @SerializedName("intAwayScore")
        val intAwayScore:String?="",
//        @SerializedName("strAwayRedCards")
//        val strAwayRedCards:String?="",
//        @SerializedName("strAwayYellowCards")
//        val strAwayYellowCards:String?="",
        @SerializedName("strAwayGoalDetails")
        val strAwayGoalDetails:String?="",
        @SerializedName("strAwayLineupGoalkeeper")
        val strAwayLineupGoalkeeper:String?="",
        @SerializedName("strAwayLineupDefense")
        val strAwayLineupDefense:String?="",
        @SerializedName("strAwayLineupMidfield")
        val strAwayLineupMidfield:String?="",
        @SerializedName("strAwayLineupForward")
        val strAwaylineupForward:String?="",
        @SerializedName("strAwayLineupSubstitutes")
        val strAwayLineupSubstitutes:String?="",
        @SerializedName("intHomeShots")
        val intHomeShots:String?="",
        @SerializedName("intAwayShots")
        val intAwayShots:String?="",
        @SerializedName("strDate")
        val strDate:String?=""
): Parcelable


